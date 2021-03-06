package pl.poznan.put.core.rnamatrix.filtering;

import pl.poznan.put.core.rnamatrix.model.Matrix;
import pl.poznan.put.core.rnamatrix.model.MtxChain;
import pl.poznan.put.core.rnamatrix.model.MtxModel;
import pl.poznan.put.core.rnamatrix.specification.ModelSpecification;

import java.util.List;
import java.util.stream.Collectors;

public interface ModelFiltering<T extends Matrix, U> {

    Integer getNumber();

    List<MtxChain<T, U>> getMtxChains();

    default MtxModel<T, U> filterChains(ModelSpecification<U> modelSpecification) {
        return MtxModel.<T, U>builder()
                .number(getNumber())
                .mtxChains(getMtxChains().stream()
                        .filter(mtxChain -> modelSpecification.getChainSpecifications().keySet().contains(mtxChain.getId()))
                        .map(tMtxChain -> tMtxChain.filterMatrix(modelSpecification.getChainSpecifications().get(tMtxChain.getId())))
                        .collect(Collectors.toList()))
                .build();
    }
}
