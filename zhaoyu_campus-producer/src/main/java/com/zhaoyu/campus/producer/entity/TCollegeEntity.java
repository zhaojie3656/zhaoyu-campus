package com.zhaoyu.campus.producer.entity;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**		
 * 数据库类型：mysql
 * @author：zhaoyu
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TCollegeEntity  implements Serializable  {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
		
	private Integer collegeId;
		
	private String collegeName;
		
	private String collegeAddress;
		
	private Integer cT;
		
	private Integer uT;
		
	private Integer isDeleted;
		
	public Integer getId(){
		return this.id;
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getCollegeId(){
		return this.collegeId;
	}
	
	public void setCollegeId(Integer collegeId){
		this.collegeId = collegeId;
	}
	
	public String getCollegeName(){
		return this.collegeName;
	}
	
	public void setCollegeName(String collegeName){
		this.collegeName = collegeName;
	}
	
	public String getCollegeAddress(){
		return this.collegeAddress;
	}
	
	public void setCollegeAddress(String collegeAddress){
		this.collegeAddress = collegeAddress;
	}
	
	public Integer getCT(){
		return this.cT;
	}
	
	public void setCT(Integer cT){
		this.cT = cT;
	}
	
	public Integer getUT(){
		return this.uT;
	}
	
	public void setUT(Integer uT){
		this.uT = uT;
	}
	
	public Integer getIsDeleted(){
		return this.isDeleted;
	}
	
	public void setIsDeleted(Integer isDeleted){
		this.isDeleted = isDeleted;
	}
	
	
	public TCollegeEntity() {
		super();
	}
	
	@Override
	public String toString() {
		return "TCollegeEntity ["
			+"id=" + this.id+"," 
			+"collegeId=" + this.collegeId+"," 
			+"collegeName=" + this.collegeName+"," 
			+"collegeAddress=" + this.collegeAddress+"," 
			+"cT=" + this.cT+"," 
			+"uT=" + this.uT+"," 
			+"isDeleted=" + this.isDeleted 
		+ "]";
	}

}