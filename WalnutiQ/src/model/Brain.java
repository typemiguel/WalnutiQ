package model;

import model.MARK_II.Neocortex;

import model.MARK_II.Region;

/**
 * The Hindbrain
 *   - Medulla Oblongata = controls breathing, muscle tone and blood
 *                         pressure.
 *   - Pons = connected to the cerebellum & involved in sleep and
 *            arousal.
 *   - Cerebellum = coordination and timing of voluntary movements, sense
 *                  of equilibrium, language, attention, ...
 *
 *   - midbrain = controls eye movements, visual and auditory reflexes
 *   - reticular formation = modulates muscle reflexes, breathing & pain
 *                           perception. Also regulates sleep, wakefulness &
 *                           arousal. Near center of brain
 *   - Thalamus = "Relay station" for all sensory information(except smell)
 *                which is conveyed to the cortex. The thalamus also regulates
 *                sleep/wakefulness.
 *   - Hypothalamus = regulates basic needs(fighting, fleeing, feeding, and mating)
 *
 * Cerebrum
 *   - Cerebral cortex = layered sheet of neurons(30 billion neurons with
 *                       10,000 synapses each makes 300 trillion connections)
 *   - Neocortex = is the outer layer of the cerebral hemispheres. Made up of 6
 *                 layers, labelled I to VI(with VI being the innermost).
 *                 I = feedback from higher cortical layers
 *                 II+III = output to higher cortical layers
 *                 IV = input to neocortex from thalamus
 *                 V =
 *                 VI = output of neocortex back to thalamus
 *   - basal ganglia
 *   - hippocampus
 *   - amygdala
 */
public class Brain {
    private Cerebrum cerebrum;
    private Thalamus thalamus;
    private Hindbrain hindbrain;
    private Midbrain midbrain;
    private ReticularFormation reticularFormation;
    private Hypothalamus hypothalamus;

    public Brain(Neocortex neocortex, Region region) {
	this.cerebrum = new Cerebrum(neocortex);
	this.thalamus = new Thalamus(region);
	this.hindbrain = null;
	this.midbrain = null;
	this.reticularFormation = null;
	this.hypothalamus = null;
    }

    public Cerebrum getCerebrum() {
	return this.cerebrum;
    }

    public Thalamus getThalamus() {
	return this.thalamus;
    }
}
