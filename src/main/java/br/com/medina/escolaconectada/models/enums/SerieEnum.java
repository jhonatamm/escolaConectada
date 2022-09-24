package br.com.medina.escolaconectada.models.enums;

public enum SerieEnum {
	
	PRIMEIRA_SERIE(1,"Primeira série"),
	SEGUNDA_SERIE(2, "Segunda série"),
	TERCEIRA_SERIE(3,"Terceira  série"),
	QUARTA_SERIE(4, "Quarta série");

	private int id;
	private String description;

	private SerieEnum(int id,String description) {
		this.id = id;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public static SerieEnum toEnum(Integer id) {

		if (id == null) {
			return null;
		}

		for (SerieEnum x : SerieEnum.values()) {
			if (id.equals(x.getId())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id invalido: " +id);
	}

}
