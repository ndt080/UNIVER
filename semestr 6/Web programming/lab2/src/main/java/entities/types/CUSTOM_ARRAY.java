package entities.types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.mapping.Array;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.IntegerTypeDescriptor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CUSTOM_ARRAY extends AbstractSingleColumnStandardBasicType<Array> {
    public CUSTOM_ARRAY() {
        super(new IntegerTypeDescriptor(), new CUSTOM_ARRAYDescriptor());
    }

    @Override
    public String getName() {
        return "CUSTOM_ARRAY";
    }

    @Override
    public Object resolve(Object value, SharedSessionContractImplementor session, Object owner, Boolean overridingEager) throws HibernateException {
        return null;
    }
}
