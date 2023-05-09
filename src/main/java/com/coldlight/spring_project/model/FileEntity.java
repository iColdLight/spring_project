package com.coldlight.spring_project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "files")
@SQLDelete(sql = "update file_entity set deleted=true where id=?")
@Where(clause = "deleted = false")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "file_path")
    private String filePath;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "file", fetch = FetchType.LAZY)
    private List<EventEntity> events = new ArrayList<>();
    @Builder.Default
    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;


}
