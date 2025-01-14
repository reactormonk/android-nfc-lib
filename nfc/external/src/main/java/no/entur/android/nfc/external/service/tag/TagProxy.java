package no.entur.android.nfc.external.service.tag;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;

import java.util.List;

import no.entur.android.nfc.wrapper.TagImpl;
import no.entur.android.nfc.wrapper.tech.Ndef;
import no.entur.android.nfc.wrapper.tech.NdefFormatable;

public class TagProxy {

	private int handle;
	private int slotNumber;

	private List<TagTechnology> technologies;

	private TagTechnology current;

	private boolean present = true;

	public TagProxy(int handle, int slotNumber, List<TagTechnology> technologies) {
		this.handle = handle;
		this.slotNumber = slotNumber;
		this.technologies = technologies;
	}

	public TagTechnology getCurrent() {
		return current;
	}

	public void setCurrent(TagTechnology current) {
		this.current = current;
	}

	public int getHandle() {
		return handle;
	}

	public void setHandle(int id) {
		this.handle = id;
	}

	public int getSlotNumber() {
		return slotNumber;
	}

	public void setSlotNumber(int slotNumber) {
		this.slotNumber = slotNumber;
	}

	public List<TagTechnology> getTechnologies() {
		return technologies;
	}

	public void setTechnologies(List<TagTechnology> technologies) {
		this.technologies = technologies;
	}

	public boolean add(TagTechnology object) {
		return technologies.add(object);
	}

	public TagTechnology getTechnology(int technology) {
		for (TagTechnology tech : technologies) {
			if (tech.getTagTechnology() == technology) {
				return tech;
			}
		}
		return null;
	}

	public boolean selectTechnology(int technology) {
		for (TagTechnology tech : technologies) {
			if (tech.getTagTechnology() == technology) {
				this.current = tech;

				return true;
			}
		}

		return false;
	}

	public void closeTechnology() {
		this.current = null;
	}

	public TagImpl rediscover(Object callback) {
		throw new RuntimeException();
	}

	public boolean isPresent() {
		return present;
	}

	public void setPresent(boolean present) {
		this.present = present;
	}

	public boolean isNdef() {
		for(TagTechnology t : technologies) {
			if(t instanceof Ndef) {
				return true;
			}
		}
		return false;
	}

	public boolean ndefIsWritable() {
		for(TagTechnology t : technologies) {
			if(t instanceof Ndef) {
				Ndef ndef = (Ndef)t;
				return ndef.isWritable();
			}
		}
		return false;
	}
}
