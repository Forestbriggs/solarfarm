package learn.solar.models;

public enum Material {
    MONOCRYSTALLINE_SILICON("mono-Si", "monocrystalline silicon"),
    MULTICRYSTALLINE_SILICON("multi-Si", "multicrystalline silicon"),
    AMORPHOUS_SILICON("a-Si", "amorphous silicon"),
    CADMIUM_TELLURIDE("CdTe", "cadmium telluride"),
    COPPER_INDIUM_GALLIUM_SELENIDE("CIGS", "copper indium gallium selenide");

    private final String abbreviatedName;
    private final String fullName;

    private Material(String abbreviatedName, String fullName) {
        this.abbreviatedName = abbreviatedName;
        this.fullName = fullName;
    }

    public String getAbbreviatedName() {
        return abbreviatedName;
    }

    public String getFullName() {
        return fullName;
    }

    public static Material findByAbbreviatedName(String abrvName) throws RuntimeException {
        for (Material m : Material.values()) {
            if (m.getAbbreviatedName().equalsIgnoreCase(abrvName)) {
                return m;
            }
        }
        String message = String.format("No material has the abbreviated name: %s", abrvName);
        throw new RuntimeException(message);
    }
}
