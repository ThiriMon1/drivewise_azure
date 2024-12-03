package edu.miu.cse.drivewise.repository;

import edu.miu.cse.drivewise.dto.request.CarCriteriaRequest;
import edu.miu.cse.drivewise.model.Inventory;
import edu.miu.cse.drivewise.model.Make;
import edu.miu.cse.drivewise.model.State;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class CarDao {
    private final EntityManager em;
    public List<Inventory> findAllByCriteria(CarCriteriaRequest carCriteriaRequest){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Inventory> cq = cb.createQuery(Inventory.class);
        List<Predicate> predicates = new ArrayList<>();

        Root<Inventory> inventoryRoot = cq.from(Inventory.class);
        Join<Inventory,Make> makeJoin = inventoryRoot.join("make", JoinType.INNER);
        Join<Inventory,Model> modelJoin = inventoryRoot.join("model", JoinType.INNER);
        Join<Inventory, State> stateJoin = inventoryRoot.join("state", JoinType.INNER);

        if(carCriteriaRequest.getMake() != null) {
            predicates.add(cb.equal(makeJoin.get("makeName"), carCriteriaRequest.getMake()));
        }
        if(carCriteriaRequest.getModel() != null) {
            predicates.add(cb.equal(modelJoin.get("modelName"), carCriteriaRequest.getModel()));
        }
        if(carCriteriaRequest.getStateName() != null) {
            predicates.add(cb.equal(stateJoin.get("stateName"), carCriteriaRequest.getStateName()));
        }
        if(carCriteriaRequest.getCarCondition() != null) {
            predicates.add(cb.equal(inventoryRoot.get("carCondition"), carCriteriaRequest.getCarCondition()));
        }
        if(carCriteriaRequest.getStyle() != null) {
            predicates.add(cb.equal(inventoryRoot.get("bodyType"), carCriteriaRequest.getStyle()));
        }
        if(carCriteriaRequest.getMaxMileage() !=null){
            predicates.add(cb.lessThanOrEqualTo(inventoryRoot.get("mileage"), carCriteriaRequest.getMaxMileage()));
        }
        if(carCriteriaRequest.getMinMileage()!=null){
            predicates.add(cb.greaterThanOrEqualTo(inventoryRoot.get("mileage"), carCriteriaRequest.getMinMileage()));
        }
        if(carCriteriaRequest.getMaxPrice()!=null){
            predicates.add(cb.lessThanOrEqualTo(inventoryRoot.get("currentPrice"), carCriteriaRequest.getMaxPrice()));
        }
        if(carCriteriaRequest.getMinPrice()!=null){
            predicates.add(cb.greaterThanOrEqualTo(inventoryRoot.get("currentPrice"), carCriteriaRequest.getMinPrice()));
        }
        if(carCriteriaRequest.getMaxYear()!=null){
            predicates.add(cb.lessThanOrEqualTo(inventoryRoot.get("year"), carCriteriaRequest.getMaxYear()));
        }
        if(carCriteriaRequest.getMinYear()!=null){
            predicates.add(cb.greaterThanOrEqualTo(inventoryRoot.get("year"), carCriteriaRequest.getMinYear()));
        }
        if(carCriteriaRequest.getFuelType()!=null){
            predicates.add(cb.equal(inventoryRoot.get("fuelType"), carCriteriaRequest.getFuelType()));
        }

        cq.select(inventoryRoot).where(predicates.toArray(new Predicate[0]));
        return em.createQuery(cq).getResultList();

    }
}
