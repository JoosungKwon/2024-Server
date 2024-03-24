package com.example.demo.src.post.entity;

import com.example.demo.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "post_file")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "uuid", length = 36, nullable = false)
    private String uuid;

    @NotBlank
    @Column(name = "fileName", length = 100, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private FileType type;

    @NotBlank
    @Column(name = "path", nullable = false)
    private String filePath;

    @NotNull
    @Min(0)
    @Max(1000000000)
    @Column(name = "size", nullable = false)
    private Long size; // byte

    private String region;

    private String bucketName;

    @Enumerated(EnumType.STRING)
    private UploadStatus uploadStatus;

    @NotNull
    @Column(name = "postId", nullable = false)
    private Long postId;

    @Column(name = "userId")
    private Long userId;

    @Setter
    @Transient
    private String preSignedUrl;

    @Builder
    public PostFile(String uuid, String name, FileType type,
                    String filePath, String region, String bucketName,
                    Long size, Long postId, Long userId) {
        this.uuid = uuid;
        this.name = name;
        this.type = type;
        this.filePath = filePath;
        this.region = region;
        this.bucketName = bucketName;
        this.size = size;
        this.postId = postId;
        this.userId = userId;
    }

    public void changeUploadStatus(UploadStatus uploadStatus) {
        this.uploadStatus = uploadStatus;
    }
}
