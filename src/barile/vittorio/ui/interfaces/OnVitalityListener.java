package barile.vittorio.ui.interfaces;

/**
 * Definisce l'abilita' di subire mutazioni della vitalita'
 * @author Vittorio
 */
public interface OnVitalityListener {
    public void addDamage(int player, int damage);
    public void addHeal(int player, int heal);
}
