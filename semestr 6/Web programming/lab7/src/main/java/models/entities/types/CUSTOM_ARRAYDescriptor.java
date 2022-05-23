package models.entities.types;

import org.hibernate.mapping.Array;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;
import org.hibernate.type.descriptor.spi.JdbcRecommendedSqlTypeMappingContext;

import java.util.Properties;

public class CUSTOM_ARRAYDescriptor extends AbstractTypeDescriptor<Array> {

    protected CUSTOM_ARRAYDescriptor() {
        super(Array.class, new ImmutableMutabilityPlan<>());
    }

    @Override
    public String toString(Array value) {
        return null;
    }

    @Override
    public Array fromString(String string) {
        return null;
    }

    @Override
    public <X> X unwrap(Array value, Class<X> type, WrapperOptions options) {
        return null;
    }

    @Override
    public <X> Array wrap(X value, WrapperOptions options) {
        return null;
    }

    @Override
    public SqlTypeDescriptor getJdbcRecommendedSqlType(JdbcRecommendedSqlTypeMappingContext context) {
        return null;
    }


}
