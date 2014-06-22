package model.unimplementedBiology;

import model.OldRetina;

/**
 * Nerves connecting to voluntary skeletal muscles and sensory receptors using
 * two types of directed Segments(Axons).
 * <p/>
 * 1) Afferent nerve fibers(segments) =
 * incoming axons that carry activation activity away from the periphery(muscles
 * and senses) to the CNS.
 * <p/>
 * 2) Efferent nerve fibers(segments) = outgoing axons that carry activation
 * activity from the CNS outward to the periphery(muscles and senses)
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version June 5, 2013
 */
public class SNS {
    private OldRetina retine;

    public SNS(OldRetina retine) {
        this.retine = retine;
    }

    public OldRetina getRetine() {
        return this.retine;
    }
}
