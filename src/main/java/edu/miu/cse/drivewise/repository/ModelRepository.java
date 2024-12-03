package edu.miu.cse.drivewise.repository;

import edu.miu.cse.drivewise.model.CarModel;
import edu.miu.cse.drivewise.model.Make;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ModelRepository extends JpaRepository<CarModel, Long> {
    Optional<CarModel> getModelByModelName(String modelName);

    @Query(value = "select cm.make from CarModel cm join cm.make m where cm.make.makeId=m.makeId and cm.modelName=:modelName")
    Optional<Make> getMakeByModelName(String modelName);

    List<CarModel> findCarModelByMake_MakeName(String makeName);


}
