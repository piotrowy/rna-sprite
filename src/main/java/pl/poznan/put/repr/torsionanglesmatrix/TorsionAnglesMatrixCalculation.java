package pl.poznan.put.repr.torsionanglesmatrix;

import pl.poznan.put.pdb.analysis.PdbChain;
import pl.poznan.put.pdb.analysis.PdbCompactFragment;
import pl.poznan.put.pdb.analysis.PdbResidue;
import pl.poznan.put.rna.torsion.RNATorsionAngleType;
import pl.poznan.put.core.rnacommons.RnaUtils;
import pl.poznan.put.core.rnamatrix.calculation.MatrixCalculation;
import pl.poznan.put.core.structure.models.AngleData;
import pl.poznan.put.core.structure.models.ResidueVm;
import pl.poznan.put.torsion.MasterTorsionAngleType;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Named;

@Named
public class TorsionAnglesMatrixCalculation implements MatrixCalculation<TorsionAnglesMatrix, Set<String>> {

    private static final String INVALID = "invalid";
    private static final String EMPTY = "0";

    private List<List<String>> parseFragment(final PdbCompactFragment fragment, final Set<RNATorsionAngleType> angles) {
        return fragment.getResidues()
                .stream()
                .map(residue -> parseResidue(fragment, residue, angles))
                .collect(Collectors.toList());
    }

    private List<String> parseResidue(final PdbCompactFragment fragment, final PdbResidue residue, final Set<RNATorsionAngleType> angles) {
        return angles.stream()
                .map(angle -> setAngleValue(fragment, residue, angle))
                .collect(Collectors.toList());
    }

    private String setAngleValue(final PdbCompactFragment fragment, final PdbResidue residue, final MasterTorsionAngleType angle) {
        if (fragment.getTorsionAngleValue(residue, angle).getValue().toString().equalsIgnoreCase(INVALID)) {
            return EMPTY;
        } else {
            return DECIMAL_FORMAT_2.format(fragment.getTorsionAngleValue(residue, angle).getValue().getDegrees());
        }
    }

    private List<String> getXLabels(Set<RNATorsionAngleType> angles) {
        return angles.stream()
                .map(RNATorsionAngleType::getShortDisplayName)
                .collect(Collectors.toList());
    }

    private List<ResidueVm> getYLabels(final PdbCompactFragment fragment) {
        return fragment.getResidues()
                .stream()
                .map(ResidueVm::residueInfo)
                .collect(Collectors.toList());
    }

    @Override
    public List<TorsionAnglesMatrix> calculate(PdbChain chain, Set<String> angles) {
        PdbCompactFragment fragment = MatrixCalculation.pdbChainToCompactFragment(chain);
        return Collections.singletonList(TorsionAnglesMatrix.builder()
                .xLabels(getXLabels(RnaUtils.mapToRnaTorsionAngleType(angles)))
                .yLabels(getYLabels(fragment))
                .data(parseFragment(fragment, RnaUtils.mapToRnaTorsionAngleType(angles)))
                .build());
    }


}
