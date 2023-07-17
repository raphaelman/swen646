package swen646.edwenson.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;

public class AccountReservationSerializer extends StdSerializer<ArrayList<String>> {

    protected AccountReservationSerializer(Class<ArrayList<String>> t) {
        super(t);
    }

    @Override
    public void serialize(ArrayList<String> strings, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

    }
}
