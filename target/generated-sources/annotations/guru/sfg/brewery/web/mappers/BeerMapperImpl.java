package guru.sfg.brewery.web.mappers;

import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.web.model.BeerDto;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-02-26T15:13:01+0100",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.10 (Oracle Corporation)"
)
@Component
@Primary
public class BeerMapperImpl extends BeerMapperDecorator implements BeerMapper {

    @Autowired
    @Qualifier("delegate")
    private BeerMapper delegate;

    @Override
    public Beer beerDtoToBeer(BeerDto beerDto)  {
        return delegate.beerDtoToBeer( beerDto );
    }
}
