package utils;

public enum TipoDiContatto {
    Email(1),
    Telefono(2),
    Cellulare(3);

    private final int id;

    private TipoDiContatto(int id) {
        this.id = id;
    }

    public static TipoDiContatto fromInt(int id) {
        for (TipoDiContatto tipo : values()) {
            if (tipo.getId() == id) {
                return tipo;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }
}
