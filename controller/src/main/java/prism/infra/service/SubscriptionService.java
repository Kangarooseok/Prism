package prism.infra.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prism.domain.subscription.repository.SubscriptionDao;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private static final long UNASSIGNED_GROUP_ID = 2L;

    private final SubscriptionDao dao;

    public List<Long> getManagerIdsByGroup(long groupId) {
        return dao.findManagerIdsByGroup(groupId);
    }

    /** 단일 그룹 원칙: 어떤 그룹에 배정할 때 기존 배정(미정 포함)은 전부 제거 후 새 그룹으로 1건 삽입 */
    @Transactional(transactionManager = "subscriptionTxManager")
    public void assign(long groupId, long userId) {
        dao.deleteAllByManager(userId);
        dao.insertOne(groupId, userId);
    }

    /** 특정 그룹에서 해제 → 미정(2)으로 이동 */
    @Transactional(transactionManager = "subscriptionTxManager")
    public void unassignToUnassigned(long groupId, long userId) {
        dao.deleteOne(groupId, userId);
        if (!dao.existsByManager(userId)) {
            dao.insertOne(UNASSIGNED_GROUP_ID, userId);
        }
    }

    @Transactional(transactionManager = "subscriptionTxManager")
    public void moveAllFromGroupToUnassigned(long fromGroupId) {
        dao.moveAllFromGroupTo(fromGroupId, UNASSIGNED_GROUP_ID); // 2L 가정
    }
}
