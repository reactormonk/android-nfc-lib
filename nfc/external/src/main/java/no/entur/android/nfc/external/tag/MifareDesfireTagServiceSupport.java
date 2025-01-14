package no.entur.android.nfc.external.tag;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import no.entur.android.nfc.external.ExternalNfcReaderCallback;
import no.entur.android.nfc.external.ExternalNfcTagCallback;
import no.entur.android.nfc.external.service.tag.TagProxyStore;
import no.entur.android.nfc.external.service.tag.TagTechnology;
import no.entur.android.nfc.wrapper.INfcTag;

public class MifareDesfireTagServiceSupport extends AbstractTagServiceSupport {

    private static final String TAG = MifareDesfireTagServiceSupport.class.getName();

    protected MifareDesfireTagFactory mifareDesfireTagFactory = new MifareDesfireTagFactory();

    public MifareDesfireTagServiceSupport(Context context, INfcTag tagService, TagProxyStore store) {
        super(context, tagService, store);
    }

    public void hce(int slotNumber, byte[] atr, AbstractReaderIsoDepWrapper wrapper, byte[] uid) {
        try {
            List<TagTechnology> technologies = new ArrayList<>();
            technologies.add(new NfcAAdapter(slotNumber, wrapper, true));
            technologies.add(new IsoDepAdapter(slotNumber, wrapper, true));

            int serviceHandle = store.add(slotNumber, technologies);

            Intent intent = mifareDesfireTagFactory.getTag(serviceHandle, slotNumber, atr, null, uid, true, TechnologyType.getHistoricalBytes(atr), tagService);

            Log.d(TAG, "Broadcast hce");

            context.sendBroadcast(intent, ANDROID_PERMISSION_NFC);
        } catch (Exception e) {
            Log.d(TAG, "Problem reading from tag", e);

            broadcast(ExternalNfcTagCallback.ACTION_TECH_DISCOVERED);
        }
    }

    public void desfire(int slotNumber, byte[] atr, AbstractReaderIsoDepWrapper wrapper, byte[] uid) {
        try {
            List<TagTechnology> technologies = new ArrayList<>();
            technologies.add(new NfcAAdapter(slotNumber, wrapper, false));
            technologies.add(new IsoDepAdapter(slotNumber, wrapper, false));

            int serviceHandle = store.add(slotNumber, technologies);

            Intent intent = mifareDesfireTagFactory.getTag(serviceHandle, slotNumber, atr, null, uid, false, TechnologyType.getHistoricalBytes(atr), tagService);

            Log.d(TAG, "Broadcast desfire");

            context.sendBroadcast(intent, ANDROID_PERMISSION_NFC);
        } catch (Exception e) {
            Log.d(TAG, "Problem reading from tag", e);

            broadcast(ExternalNfcTagCallback.ACTION_TECH_DISCOVERED);
        }
    }

}
