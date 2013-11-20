package com.psilonsoft.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "insecure_doc")
public class Document {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doc_seq")
    @SequenceGenerator(name = "doc_seq", sequenceName = "seq_inc_doc_id", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;
	
    @Column(name = "belongs_to_id", nullable = false)
    private Long ownerId;

    @Column(name = "title", length = 200, nullable = false)
    private String title;
    
    @Column(name = "content", length = 1000, nullable = true)
    private String content;

    public Long getId() {
        return id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(final Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Document [id=" + id + ", ownerId=" + ownerId + ", title=" + title + ", content=" + content + "]";
    }
}
