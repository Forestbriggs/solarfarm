package learn.solar.models;

public class Panel {

    private int id;
    private String section;
    private int row;
    private int column;
    private int installationYear;
    private Material material;
    private boolean tracking;

    public Panel() {
    }

    public Panel(int id, String section, int row, int column, int installationYear, Material material, boolean tracking) {
        this.id = id;
        this.section = section;
        this.row = row;
        this.column = column;
        this.installationYear = installationYear;
        this.material = material;
        this.tracking = tracking;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getInstallationYear() {
        return installationYear;
    }

    public void setInstallationYear(int installationYear) {
        this.installationYear = installationYear;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public boolean isTracking() {
        return tracking;
    }

    public void setTracking(boolean tracking) {
        this.tracking = tracking;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((section == null) ? 0 : section.hashCode());
        result = prime * result + row;
        result = prime * result + column;
        result = prime * result + installationYear;
        result = prime * result + ((material == null) ? 0 : material.hashCode());
        result = prime * result + (tracking ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Panel other = (Panel) obj;
        if (id != other.id) {
            return false;
        }
        if (section == null) {
            if (other.section != null) {
                return false;
            }
        } else if (!section.equals(other.section)) {
            return false;
        }
        if (row != other.row) {
            return false;
        }
        if (column != other.column) {
            return false;
        }
        if (installationYear != other.installationYear) {
            return false;
        }
        if (material != other.material) {
            return false;
        }
        if (tracking != other.tracking) {
            return false;
        }
        return true;
    }

}
