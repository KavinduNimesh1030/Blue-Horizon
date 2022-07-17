package view.tm;

public class ReservationTM {
    private String revId;
    private String cId;
    private String rId;

    public ReservationTM() {
    }

    public ReservationTM(String revId, String cId, String rId) {
        this.revId = revId;
        this.cId = cId;
        this.rId = rId;
    }

    public String getRevId() {
        return revId;
    }

    public void setRevId(String revId) {
        this.revId = revId;
    }

    public String getCId() {
        return cId;
    }

    public void setCId(String cId) {
        this.cId = cId;
    }

    public String getRId() {
        return rId;
    }

    public void setRId(String rId) {
        this.rId = rId;
    }

    @Override
    public String toString() {
        return "ReservationTM{" +
                "revId='" + revId + '\'' +
                ", cId='" + cId + '\'' +
                ", rId='" + rId + '\'' +
                '}';
    }
}

