package model.unimplementedBiology;

import model.MARK_II.Neocortex;
import model.Retina;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version June 5, 2013
 */
public class NervousSystem {
    private CNS CNS;
    private model.unimplementedBiology.PNS PNS;

    public NervousSystem(Neocortex neocortex, LGN LGN, Retina retina) {
        this.CNS = new CNS(neocortex, LGN);
        this.PNS = new PNS(retina);
    }

    public CNS getCNS() {
        return this.CNS;
    }

    public PNS getPNS() {
        return this.PNS;
    }
}
