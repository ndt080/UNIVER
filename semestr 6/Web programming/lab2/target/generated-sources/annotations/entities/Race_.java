package entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Race.class)
public abstract class Race_ {

	public static volatile SingularAttribute<Race, Date> date;
	public static volatile SingularAttribute<Race, Horse> winner;
	public static volatile SingularAttribute<Race, Integer> id;

}

