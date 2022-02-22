package model;

import java.util.Date;

public class Licence {
    
    private int id;
    private String licenceHardwareId;
    private int licenceStatus;
    private Date licenceValidity;
    private int clientId;
    private int softwareId;

    public Licence(int id, String licenceHardwareId, int licenceStatus,
    		Date licenceValidity, int clientId, int softwareId) {
        this.id = id;
        this.licenceHardwareId = licenceHardwareId;
        this.licenceStatus = licenceStatus;
        this.licenceValidity = licenceValidity;
        this.clientId = clientId;
        this.softwareId = softwareId;
    }
    
    // Licence ID
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
         this.id = id;
    }
    
    // Licence hardware ID
    public String getLicenceHardwareId() {
        return licenceHardwareId;
    }
    
    public void setLicenceHardwareId(String licenceHardwareId) {
        this.licenceHardwareId = licenceHardwareId;
    }
    
    // Licence status
    public int getLicenceStatus() {
        return licenceStatus;
    }
    
    public void setLicenceStatus(int licenceStatus) {
        this.licenceStatus = licenceStatus;
    }
    
    // Licence validity date
    public Date getLicenceValidity() {
        return licenceValidity;
    }
    
    public void setLicenceValidity(Date licenceValidity) {
        this.licenceValidity = licenceValidity ;
    }
    
    // Client Id
    public int getClientId() {
        return clientId;
    }
    
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    
    // Software ID
    public int getSoftwareId() {
        return softwareId;
    }
    
    public void setSoftwareId(int softwareId) {
        this.softwareId = softwareId;
    }
    
}