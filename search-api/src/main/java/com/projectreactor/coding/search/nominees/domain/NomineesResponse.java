package com.projectreactor.coding.search.nominees.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("oscarNominee")
public class NomineesResponse{

	@JsonProperty("winner")
	private boolean winner;

	@JsonProperty("year")
	private int year;

	@JsonProperty("category")
	private String category;

	@JsonProperty("entity")
	private String entity;

	@Override
 	public String toString(){
		return 
			"NomineesResponse{" + 
			"winner = '" + winner + '\'' + 
			",year = '" + year + '\'' + 
			",category = '" + category + '\'' + 
			",entity = '" + entity + '\'' + 
			"}";
		}
}