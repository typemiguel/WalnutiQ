package model;

import model.MARK_II.VisionCellLayer;

/**
 * Nerves connecting to voluntary skeletal muscles and sensory receptors using
 * two types of directed segments(Axons).
 *
 * 1) Afferent nerve fibers(segments) =
 * incoming axons that carry activation activity away from the periphery(muscles
 * and senses) to the CNS.
 *
 * 2) Efferent nerve fibers(segments) = outgoing axons that carry activation
 * activity from the CNS outward to the periphery(muscles and senses)
 */
public class SomaticNervousSystem {
    private Retina retina;

    public SomaticNervousSystem(Retina retina) {
	this.retina = retina;
    }

    public Retina getRetina() {
	return this.retina;
    }
}
