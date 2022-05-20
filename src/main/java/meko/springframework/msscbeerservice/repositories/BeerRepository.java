package meko.springframework.msscbeerservice.repositories;

import meko.springframework.msscbeerservice.domain.Beer;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;


public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {
}
