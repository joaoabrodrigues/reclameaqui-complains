package br.com.reclameaqui.complaints.handler;

import br.com.reclameaqui.complaints.model.Complaint;
import br.com.reclameaqui.complaints.model.Locale;
import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(Complaint.class)
public class ComplaintRepositoryHandler {

    private final JOpenCageGeocoder jOpenCageGeocoder;

    @Autowired
    public ComplaintRepositoryHandler(JOpenCageGeocoder jOpenCageGeocoder) {
        this.jOpenCageGeocoder = jOpenCageGeocoder;
    }

    @HandleBeforeCreate
    public void handleBeforeCreate(Complaint complaint) {
        Locale locale = complaint.getLocale();
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(locale.getAddress(), locale.getZipCode(), locale.getCity(), locale.getState(), locale.getCountry());
        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        response.getResults().stream().findFirst().ifPresent(result -> {
            JOpenCageLatLng geometry = result.getGeometry();
            complaint.setLat(geometry.getLat().toString());
            complaint.setLng(geometry.getLng().toString());
        });
    }
}
