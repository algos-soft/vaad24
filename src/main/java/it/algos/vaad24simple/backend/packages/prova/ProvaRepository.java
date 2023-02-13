package it.algos.vaad24simple.backend.packages.prova;

import it.algos.vaad24.backend.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Mon, 13-Feb-2023
 * Time: 09:40
 * <p>
 * Estende l'interfaccia MongoRepository col casting alla Entity relativa di questa repository <br>
 * <p>
 * Annotated with @Repository (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Eventualmente usare una costante di VaadCost come @Qualifier sia qui che nella corrispondente classe xxxBackend <br>
 */
@Repository
@Qualifier("Prova")
public interface ProvaRepository extends MongoRepository<Prova, String> {

    @Override
    List<Prova> findAll();

    <Prova extends AEntity> Prova insert(Prova entity);

    <Prova extends AEntity> Prova save(Prova entity);

    @Override
    void delete(Prova entity);

}// end of crud repository class