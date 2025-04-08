package aceleradora.socios.back.services.georef;

import aceleradora.socios.back.services.georef.entities.*;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

@Service
public class ServicioGeoref {
  private static ServicioGeoref instancia = null;
  private static int maximaCantidadRegistrosDefault = 500;
  private static final String urlApi = "https://apis.datos.gob.ar/georef/api/";//TODO Sacar url de un archivo de configuracion
  private Retrofit retrofit;

  private ServicioGeoref() {
    this.retrofit = new Retrofit.Builder()
            .baseUrl(urlApi)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
  }

  public static ServicioGeoref instancia(){
    if(instancia== null){
      instancia = new ServicioGeoref();
    }
    return instancia;
  }




  public ListadoDeProvincias listadoDeProvincias() throws IOException {
    GeorefService georefService = this.retrofit.create(GeorefService.class);
    Call<ListadoDeProvincias> requestProvinciasArgentinas = georefService.provincias();
    Response<ListadoDeProvincias> responseProvinciasArgentinas = requestProvinciasArgentinas.execute();
    return responseProvinciasArgentinas.body();
  }

  public ListadoDeMunicipios listadoDeMunicipiosDeProvincia(Provincia provincia) throws IOException {
    GeorefService georefService = this.retrofit.create(GeorefService.class);
    Call<ListadoDeMunicipios> requestListadoDeMunicipios = georefService.municipios(provincia.getId(), "id, nombre", maximaCantidadRegistrosDefault);
    Response<ListadoDeMunicipios> responseListadoDeMunicipios = requestListadoDeMunicipios.execute();
    return responseListadoDeMunicipios.body();
  }

  public ListadoDeLocalidades listadoDeLocalidadesDeMunicipios(Municipio municipio) throws IOException {
    GeorefService georefService = this.retrofit.create(GeorefService.class);
    Call<ListadoDeLocalidades> requestListadoDeLocalidades = georefService.localidades(municipio.getId(), "id, nombre", maximaCantidadRegistrosDefault);
    Response<ListadoDeLocalidades> responseListadoDeLocalidades = requestListadoDeLocalidades.execute();
    return responseListadoDeLocalidades.body();
  }

  public ListadoDeLocalidades listadoDeLocalidadesDeMunicipios1(Provincia provincia) throws IOException {
    GeorefService georefService = this.retrofit.create(GeorefService.class);
    Call<ListadoDeLocalidades> requestListadoDeLocalidades = georefService.localidades1(provincia.getId(), "id, nombre", maximaCantidadRegistrosDefault);
    Response<ListadoDeLocalidades> responseListadoDeLocalidades = requestListadoDeLocalidades.execute();
    return responseListadoDeLocalidades.body();
  }



}