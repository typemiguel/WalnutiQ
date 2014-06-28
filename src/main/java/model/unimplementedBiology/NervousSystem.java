package model.unimplementedBiology;

import model.MARK_II.Neocortex;
import model.OldRetina;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version June 5, 2013
 */
public class NervousSystem {
    private CNS CNS;
    private model.unimplementedBiology.PNS PNS;

    public NervousSystem(Neocortex neocortex, LGN LGN, OldRetina oldRetina) {
        this.CNS = new CNS(neocortex, LGN);
        this.PNS = new PNS(oldRetina);
    }

    public CNS getCNS() {
        return this.CNS;
    }

    public PNS getPNS() {
        return this.PNS;
    }
}
