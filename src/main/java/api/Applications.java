
package api;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "applications")
@NamedQueries({@NamedQuery(name = "Applications.findAll", query = "SELECT a FROM Applications a"), 
    @NamedQuery(name = "Applications.findByDeploymentDate", query = "SELECT a FROM Applications a WHERE a.deploymentDate = :deploymentDate"), 
    @NamedQuery(name = "Applications.findById", query = "SELECT a FROM Applications a WHERE a.id = :id"), 
    @NamedQuery(name = "Applications.findByCriticalRating", query = "SELECT a FROM Applications a WHERE a.criticalRating = :criticalRating"), 
    @NamedQuery(name = "Applications.findByCreatedAt", query = "SELECT a FROM Applications a WHERE a.createdAt = :createdAt"), 
    @NamedQuery(name = "Applications.findByHostingEnvironment", query = "SELECT a FROM Applications a WHERE a.hostingEnvironment = :hostingEnvironment"), 
    @NamedQuery(name = "Applications.findByVersionNumber", query = "SELECT a FROM Applications a WHERE a.versionNumber = :versionNumber"), 
    @NamedQuery(name = "Applications.findByDatabaseType", query = "SELECT a FROM Applications a WHERE a.databaseType = :databaseType"), 
    @NamedQuery(name = "Applications.findByOperatingSystem", query = "SELECT a FROM Applications a WHERE a.operatingSystem = :operatingSystem"), 
    @NamedQuery(name = "Applications.findByAppName", query = "SELECT a FROM Applications a WHERE a.appName = :appName"), 
    @NamedQuery(name = "Applications.findByAuthenticationMethod", query = "SELECT a FROM Applications a WHERE a.authenticationMethod = :authenticationMethod"), 
    @NamedQuery(name = "Applications.findByOwner", query = "SELECT a FROM Applications a WHERE a.owner = :owner"), 
    @NamedQuery(name = "Applications.findByTransactionVolume", query = "SELECT a FROM Applications a WHERE a.transactionVolume = :transactionVolume"), 
    @NamedQuery(name = "Applications.findByUserBase", query = "SELECT a FROM Applications a WHERE a.userBase = :userBase"), 
    @NamedQuery(name = "Applications.findByVendor", query = "SELECT a FROM Applications a WHERE a.vendor = :vendor")})
public class Applications implements Serializable {
  private static final long serialVersionUID = 1L;
  
public enum HostingEnvironment { ON_PREMISE, CLOUD, HYBRID; }

public enum CriticalRating { HIGH, MEDIUM, LOW, CRITICAL;}
  

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @JsonbProperty("id")
  @Column(name = "id")
  private Integer id;
  
 @JsonbProperty("deploymentDate")
@JsonbDateFormat("yyyy/MM/dd")
@Column(name = "deployment_date")
@Temporal(TemporalType.TIMESTAMP)
private Date deploymentDate;
 

@JsonbProperty("createdA")
@Column(name = "created_at")
@Temporal(TemporalType.TIMESTAMP)
private Date createdAt;

  @Enumerated(EnumType.STRING)
  @Basic(optional = false)
  @NotNull
  @JsonbProperty("critical_rating")
  @Column(name = "critical_rating")
  private CriticalRating criticalRating;
    
  @Enumerated(EnumType.STRING)
  @Basic(optional = false)
  @NotNull
  @JsonbProperty("hosting_environment")
  @Column(name = "hosting_environment")
  private HostingEnvironment hostingEnvironment;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 50)
  @JsonbProperty("version_number")
  @Column(name = "version_number")
  private String versionNumber;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @JsonbProperty("database_type")
  @Column(name = "database_type")
  private String databaseType;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @JsonbProperty("operating_system")
  @Column(name = "operating_system")
  private String operatingSystem;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @JsonbProperty("app_name")
  @Column(name = "app_name")
  private String appName;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @JsonbProperty("authentication_method")
  @Column(name = "authentication_method")
  private String authenticationMethod;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @JsonbProperty("owner")
  @Column(name = "owner")
  private String owner;
  
  @Size(max = 255)
  @JsonbProperty("transaction_volume")
  @Column(name = "transaction_volume")
  private String transactionVolume;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @JsonbProperty("user_base")
  @Column(name = "user_base")
  private String userBase;
  
  
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @JsonbProperty("vendor")
  @Column(name = "vendor")
  private String vendor;
  
  @Basic(optional = false)
  @NotNull
  @Lob
  @Size(min = 1, max = 65535)
  @JsonbProperty("data_types")
  @Column(name = "data_types")
  private String dataTypes;
  
  @Lob
  @Size(max = 65535)
  @JsonbProperty("integrated_apps")
  @Column(name = "integrated_apps")
  private String integratedApps;
  
  @Basic(optional = false)
  @NotNull
  @Lob
  @Size(min = 1, max = 65535)
  @JsonbProperty("purpose")
  @Column(name = "purpose")
  private String purpose;
  
  @Lob
  @Size(max = 65535)
  @JsonbProperty("users")
  @Column(name = "users")
  private String users;
  
  public Applications() {}
  
  public Applications(Integer id, Date deploymentDate, 
          CriticalRating criticalRating, HostingEnvironment hostingEnvironment,
          String versionNumber, String databaseType, String operatingSystem, 
          String appName, String authenticationMethod, String owner, String userBase, 
          String vendor, String dataTypes, String purpose) {
    this.id = id;
    this.deploymentDate = deploymentDate;
    this.criticalRating = criticalRating;
    this.hostingEnvironment = hostingEnvironment;
    this.versionNumber = versionNumber;
    this.databaseType = databaseType;
    this.operatingSystem = operatingSystem;
    this.appName = appName;
    this.authenticationMethod = authenticationMethod;
    this.owner = owner;
    this.userBase = userBase;
    this.vendor = vendor;
    this.dataTypes = dataTypes;
    this.purpose = purpose;
  }
   
  public void setDeploymentDate(Date deploymentDate) {
    this.deploymentDate = deploymentDate;
  }
   
  public String getDeploymentDateFormatted() {
        return deploymentDate != null ? new SimpleDateFormat("yyyy-MM-dd").format(deploymentDate) : null;
    }
  
  public Integer getId() {
    return this.id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public CriticalRating getCriticalRating() {
    return this.criticalRating;
  }
  
  public void setCriticalRating(CriticalRating criticalRating) {
    this.criticalRating = criticalRating;
  }
    
  public HostingEnvironment getHostingEnvironment() {
    return this.hostingEnvironment;
  }
  
  public void setHostingEnvironment(HostingEnvironment hostingEnvironment) {
    this.hostingEnvironment = hostingEnvironment;
  }
  
  public String getVersionNumber() {
    return this.versionNumber;
  }
  
  public void setVersionNumber(String versionNumber) {
    this.versionNumber = versionNumber;
  }
  
  public String getDatabaseType() {
    return this.databaseType;
  }
  
  public void setDatabaseType(String databaseType) {
    this.databaseType = databaseType;
  }
  
  public String getOperatingSystem() {
    return this.operatingSystem;
  }
  
  public void setOperatingSystem(String operatingSystem) {
    this.operatingSystem = operatingSystem;
  }
  
  public String getAppName() {
    return this.appName;
  }
  
  public void setAppName(String appName) {
    this.appName = appName;
  }
  
  public String getAuthenticationMethod() {
    return this.authenticationMethod;
  }
  
  public void setAuthenticationMethod(String authenticationMethod) {
    this.authenticationMethod = authenticationMethod;
  }
  
  public String getOwner() {
    return this.owner;
  }
  
  public void setOwner(String owner) {
    this.owner = owner;
  }
  
  public String getTransactionVolume() {
    return this.transactionVolume;
  }
  
  public void setTransactionVolume(String transactionVolume) {
    this.transactionVolume = transactionVolume;
  }
  
  public String getUserBase() {
    return this.userBase;
  }
  
  public void setUserBase(String userBase) {
    this.userBase = userBase;
  }
  
  public String getVendor() {
    return this.vendor;
  }
  
  public void setVendor(String vendor) {
    this.vendor = vendor;
  }
  
  public String getDataTypes() {
    return this.dataTypes;
  }
  
  public void setDataTypes(String dataTypes) {
    this.dataTypes = dataTypes;
  }
  
  public String getIntegratedApps() {
    return this.integratedApps;
  }
  
  public void setIntegratedApps(String integratedApps) {
    this.integratedApps = integratedApps;
  }
  
  public String getPurpose() {
    return this.purpose;
  }
  
  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }
  
  public String getUsers() {
    return this.users;
  }
  
  public void setUsers(String users) {
    this.users = users;
  }
  
  public int hashCode() {
    return (this.id != null) ? this.id.hashCode() : 0;
  }
  
  public boolean equals(Object object) {
    return (object instanceof api.Applications && this.id != null && this.id.equals(((api.Applications)object).id));
  }
  
  public String toString() {
    return "api.Applications[ id=" + this.id + " ]";
  }
  
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }
  
  public String getCreatedAtFormatted() {
    return createdAt != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createdAt) : null;
}

}
