package entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Bet.class)
public abstract class Bet_ {

	public static volatile SingularAttribute<Bet, Horse> horse;
	public static volatile SingularAttribute<Bet, Date> createdAt;
	public static volatile SingularAttribute<Bet, Double> amount;
	public static volatile SingularAttribute<Bet, Admin> creator;
	public static volatile SingularAttribute<Bet, Race> race;
	public static volatile SingularAttribute<Bet, Double> coefficient;
	public static volatile SingularAttribute<Bet, Client> client;
	public static volatile SingularAttribute<Bet, Integer> id;

}

