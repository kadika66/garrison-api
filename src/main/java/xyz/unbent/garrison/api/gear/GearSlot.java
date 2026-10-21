package xyz.unbent.garrison.api.gear;

import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

/**
 * The places a guard can carry gear. A guard type decides which of these it has.
 *
 * {@link #MELEE} and {@link #RANGED} both go in the guard's hand, so a guard holds one or the
 * other, never both.
 */
public enum GearSlot {
	HEAD(EquipmentSlot.HEAD),
	CHEST(EquipmentSlot.CHEST),
	LEGS(EquipmentSlot.LEGS),
	FEET(EquipmentSlot.FEET),
	/** A melee weapon, in the main hand. */
	MELEE(EquipmentSlot.HAND),
	/** A bow or crossbow, in the main hand. */
	RANGED(EquipmentSlot.HAND),
	/** Body armour for animals that wear it, such as wolf armour. */
	BODY(EquipmentSlot.BODY);

	private final EquipmentSlot equipmentSlot;

	GearSlot(EquipmentSlot equipmentSlot) {
		this.equipmentSlot = equipmentSlot;
	}

	/** Where on the mob this slot's item is worn. */
	@NotNull
	public EquipmentSlot getEquipmentSlot() {
		return equipmentSlot;
	}

	/** True for the two hand slots, of which a guard may fill only one. */
	public boolean isWeapon() {
		return this == MELEE || this == RANGED;
	}

	/** The other hand slot, for {@link #MELEE} and {@link #RANGED}; null otherwise. */
	public GearSlot otherWeapon() {
		return this == MELEE ? RANGED : this == RANGED ? MELEE : null;
	}
}
