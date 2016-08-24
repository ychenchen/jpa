package com.embracesource.jpa.nativejpa.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity  
@Table(name="students") 
public class Students  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3000283735752049481L;

	@Id
	@Column(name = "id", length = 50)
    private Long id;
    
    @Column(name="student_code",length=50, unique=true)
    private String studentCode;
    
    @Column(name="student_name",length=50)
    private String studentName;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStudentCode() {
		return studentCode;
	}

	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

}
