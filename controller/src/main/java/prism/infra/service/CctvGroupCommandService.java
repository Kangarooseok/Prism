package prism.infra.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prism.domain.cctv.model.Cctv;
import prism.domain.cctv.repository.CctvRepository;
import prism.domain.group.model.CctvGroup;
import prism.domain.group.repository.CctvGroupRepository;
import prism.infra.dto.CctvGroupRequest;
import prism.infra.dto.CctvGroupResponse;
import prism.infra.exception.DuplicateGroupNameException;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CctvGroupCommandService {

    private final CctvGroupRepository groupRepository;
    private final CctvRepository cctvRepository;

    private static final String UNASSIGNED_GROUP_NAME = "미정";

    // CCTV들을 '미정' 그룹으로 이동
    private void moveToUnassignedGroup(List<Cctv> cctvs) {
        CctvGroup unassigned = ensureUnassignedGroupExists();
        for (Cctv cctv : cctvs) {
            cctv.setGroup(unassigned);
        }
        cctvRepository.saveAll(cctvs);
    }

    // '미정' 그룹이 없으면 생성
    private CctvGroup ensureUnassignedGroupExists() {
        Optional<CctvGroup> optional = groupRepository.findByName(UNASSIGNED_GROUP_NAME);
        return optional.orElseGet(() -> {
            CctvGroup group = new CctvGroup();
            group.setName(UNASSIGNED_GROUP_NAME);
            group.setDescription("그룹에 배정되지 않은 CCTV의 기본 그룹");
            return groupRepository.save(group);
        });
    }

    @Transactional
    public CctvGroupResponse register(CctvGroupRequest request) {

        // 그룹명 중복 검사
        if (groupRepository.existsByName(request.getName())) {
            throw new DuplicateGroupNameException("이미 존재하는 그룹명입니다.");
        }

        // 그룹 생성
        CctvGroup group = new CctvGroup();
        group.setName(request.getName());
        group.setDescription(request.getDescription());
        group.setCreatedAt(new Date());
        group.setUpdatedAt(new Date());

        CctvGroup saved = groupRepository.save(group);

        // CCTV를 그룹에 배정
        if (request.getCctvIds() != null) {
            List<Cctv> cctvs = StreamSupport.stream(
                    cctvRepository.findAllById(request.getCctvIds()).spliterator(), false
            ).collect(Collectors.toList());

            for (Cctv cctv : cctvs) {
                cctv.setGroup(saved);
            }

            cctvRepository.saveAll(cctvs);
        }

        return toResponse(saved);
    }

    @Transactional
    public CctvGroupResponse update(Long id, CctvGroupRequest request) {
        // 1) 그룹 존재 여부 확인
        CctvGroup group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        // 2) 그룹명 중복 검사 (자기 자신 제외)
        Optional<CctvGroup> existingGroupOpt = groupRepository.findByName(request.getName());
        if (existingGroupOpt.isPresent() && !existingGroupOpt.get().getId().equals(id)) {
            throw new DuplicateGroupNameException("이미 존재하는 그룹명입니다.");
        }

        // 3) 그룹 정보 수정
        group.setName(request.getName());
        group.setDescription(request.getDescription());
        group.setUpdatedAt(new Date());
        CctvGroup saved = groupRepository.save(group);

        // 4) 기존/신규 CCTV 목록 비교하여 "변경분만" 갱신
        // 기존: 현재 그룹에 속한 CCTV id들
        List<Cctv> existingCctvs = cctvRepository.findByGroupId(saved.getId());
        Set<Long> oldIds = existingCctvs.stream().map(Cctv::getId).collect(Collectors.toSet());

        // 신규: 요청으로 받은 CCTV id들(null → 빈 목록)
        List<Long> newIdsList = request.getCctvIds() != null ? request.getCctvIds() : Collections.emptyList();
        Set<Long> newIds = new HashSet<>(newIdsList);

        // 제거 대상: 기존 - 신규 → '미정' 그룹으로 이동
        List<Long> toRemove = oldIds.stream()
                .filter(idVal -> !newIds.contains(idVal))
                .collect(Collectors.toList());

        // 추가 대상: 신규 - 기존 → 이 그룹으로 이동
        List<Long> toAdd = newIds.stream()
                .filter(idVal -> !oldIds.contains(idVal))
                .collect(Collectors.toList());

        // 5) 실제 변경이 있는 것만 업데이트
        if (!toRemove.isEmpty()) {
            CctvGroup unassigned = ensureUnassignedGroupExists();
            List<Cctv> removeEntities = StreamSupport.stream(
                    cctvRepository.findAllById(toRemove).spliterator(), false
            ).collect(Collectors.toList());
            removeEntities.forEach(c -> c.setGroup(unassigned));
            cctvRepository.saveAll(removeEntities);
        }

        if (!toAdd.isEmpty()) {
            List<Cctv> addEntities = StreamSupport.stream(
                    cctvRepository.findAllById(toAdd).spliterator(), false
            ).collect(Collectors.toList());
            addEntities.forEach(c -> c.setGroup(saved));
            cctvRepository.saveAll(addEntities);
        }

        return toResponse(saved);
    }

    // 그룹 삭제
    @Transactional
    public void delete(Long id) {
        CctvGroup group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        if ("미정".equals(group.getName())) {
            throw new IllegalArgumentException("'미정' 그룹은 삭제할 수 없습니다.");
        }

        // 그룹에 속한 CCTV들을 '미정'으로 이동
        List<Cctv> cctvs = cctvRepository.findByGroupId(group.getId());
        moveToUnassignedGroup(cctvs);

        groupRepository.deleteById(id);
    }

    // 단일 그룹 조회
    public CctvGroupResponse get(Long id) throws Exception {
        CctvGroup group = groupRepository.findById(id)
                .orElseThrow(() -> new Exception("Group not found"));
        return toResponse(group);
    }

    // 전체 그룹 조회
    public List<CctvGroupResponse> getAll() {
        ensureUnassignedGroupExists(); // 항상 '미정' 그룹 보장
        return StreamSupport.stream(groupRepository.findAll().spliterator(), false)
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Entity → DTO 변환
    private CctvGroupResponse toResponse(CctvGroup group) {
        CctvGroupResponse dto = new CctvGroupResponse();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setDescription(group.getDescription());

        if (group.getCctvs() != null) {
            dto.setCctvIds(group.getCctvs().stream()
                    .map(Cctv::getId)
                    .collect(Collectors.toList()));
        } else {
            dto.setCctvIds(Collections.emptyList());
        }

        return dto;
    }
}
