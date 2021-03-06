package pl.poznan.put.core.rnamatrix.filtering;

import pl.poznan.put.core.rnamatrix.model.Matrix;
import pl.poznan.put.core.rnamatrix.model.MtxModel;
import pl.poznan.put.core.rnamatrix.model.MtxStructure;
import pl.poznan.put.core.rnamatrix.specification.StructureSpecification;

import java.util.List;
import java.util.stream.Collectors;

public interface StructureFiltering<T extends Matrix, U> {

    String getName();

    List<MtxModel<T, U>> getMtxModels();

    default MtxStructure<T, U> filterModels(StructureSpecification<U> structureSpecification) {
        return MtxStructure.<T, U>builder()
                .name(getName())
                .mtxModels(getMtxModels().stream()
                        .filter(mtxModel -> structureSpecification.getModelSpecifications().keySet().contains(mtxModel.getNumber()))
                        .map(tMtxModel -> tMtxModel.filterChains(structureSpecification.getModelSpecifications().get(tMtxModel.getNumber())))
                        .collect(Collectors.toList()))
                .build();
    }
}
