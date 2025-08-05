package prism.domain.group.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import prism.domain.cctv.model.Cctv;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cctv_group")
@Getter
@Setter
public class CctvGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                  // 그룹 ID

    private String name;              // 그룹 이름
    private String description;       // 그룹 설명
    private Date createdAt;
    private Date updatedAt;

    // 이 그룹에 속한 CCTV 리스트 (지연 로딩, 순환 참조 방지용 JSON 설정)
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Cctv> cctvs;
}
