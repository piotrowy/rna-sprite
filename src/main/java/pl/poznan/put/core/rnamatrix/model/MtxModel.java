package pl.poznan.put.core.rnamatrix.model;

import lombok.Builder;
import lombok.Value;
import pl.poznan.put.core.rnamatrix.filtering.ModelFiltering;

import java.util.List;

@Value
@Builder
public class MtxModel<T extends Matrix, U> implements ModelFiltering<T, U> {

    private final Integer number;
    private final List<MtxChain<T, U>> mtxChains;
}
