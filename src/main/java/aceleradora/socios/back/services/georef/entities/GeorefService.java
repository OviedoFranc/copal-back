package aceleradora.socios.back.services.georef.entities;

import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

@Service
public interface GeorefService {
  @GET("provincias")
  Call<ListadoDeProvincias> provincias();

  @GET("provincias")
  Call<ListadoDeProvincias> provincias(@Query("campos") String campos);

  @GET("municipios")
  Call<ListadoDeMunicipios> municipios(@Query("provincia") int idProvincia);

  @GET("municipios")
  Call<ListadoDeMunicipios> municipios(@Query("provincia") int idProvincia, @Query("campos") String campos);

  @GET("municipios")
  Call<ListadoDeMunicipios> municipios(@Query("provincia") int idProvincia, @Query("campos") String campos, @Query("max") int max);

  @GET("localidades")
  Call<ListadoDeLocalidades> localidades(@Query("municipio") int idMunicipio, @Query("campos") String campos,@Query("max") int max);

  @GET("localidades")
  Call<ListadoDeLocalidades> localidades1(@Query("provincia") int idProvincia, @Query("campos") String campos,@Query("max") int max);




}