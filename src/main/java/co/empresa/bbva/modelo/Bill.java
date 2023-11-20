package co.empresa.bbva.modelo;

import java.io.Serializable;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@NoArgsConstructor
@AllArgsConstructor
@Data
public class Bill implements Serializable {
	
	private Integer id;
	private Date date_bill;
	private Integer user_id;
	private Integer value;
	private Integer type;
	private String observation;
	

}
