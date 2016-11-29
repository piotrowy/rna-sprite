package pl.poznan.put.distancematrix;

import pl.poznan.put.exceptions.StructureIsEmptyException;
import pl.poznan.put.rnamatrix.Matrix;
import pl.poznan.put.rnamatrix.MatrixProvider;
import pl.poznan.put.structure.PdbStructure;
import pl.poznan.put.torsionanglesmatrix.AngleData;
import pl.poznan.put.torsionanglesmatrix.ResidueInfo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DistanceMatrixProvider extends MatrixProvider<String, String, String, String> {

    public DistanceMatrixProvider(final DistanceMatrixCalculation distanceMatrixCalculation) {
        super.setCalculationMethod(distanceMatrixCalculation);
    }

    @Override
    public List<Matrix<String, String, String>> get(PdbStructure structure, Optional<String> args) {
        if (!structure.getModels().isEmpty()) {
            return structure.getModels().stream().map(model -> super.getCalculationMethod().calculateMatrix(model, args)).collect(Collectors.toList());
        }
        throw new StructureIsEmptyException(structure.toString());
    }
}