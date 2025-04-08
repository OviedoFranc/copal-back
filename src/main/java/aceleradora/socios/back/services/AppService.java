package aceleradora.socios.back.services;

import aceleradora.socios.back.clases.Usuario;
import aceleradora.socios.back.clases.departamento.Autoridad;
import aceleradora.socios.back.clases.departamento.Departamento;
import aceleradora.socios.back.clases.departamento.Puesto;
import aceleradora.socios.back.clases.espacio.EstadoReserva;
import aceleradora.socios.back.clases.espacio.Lugar;
import aceleradora.socios.back.clases.espacio.Recurso;
import aceleradora.socios.back.clases.espacio.Reserva;
import aceleradora.socios.back.clases.evento.EstadoEvento;
import aceleradora.socios.back.clases.evento.Evento;
import aceleradora.socios.back.clases.evento.Modalidad;
import aceleradora.socios.back.clases.evento.Participante;
import aceleradora.socios.back.clases.evento.PlataformaEvento;
import aceleradora.socios.back.clases.evento.TipoParticipante;
import aceleradora.socios.back.clases.socio.Categoria;
import aceleradora.socios.back.clases.socio.Etiqueta;
import aceleradora.socios.back.clases.socio.Socio;
import aceleradora.socios.back.clases.ubicacion.Ubicacion;
import aceleradora.socios.back.repositorios.*;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AppService {
    private final EstadoEventoRepository estadoEventoRepository;
    private final DepartamentoRepository departamentoRepository;
    private final UbicacionRepository ubicacionRepository;
    private final ParticipanteRepository participanteRepository;
    private final SocioRepository socioRespository;
    private final EventoRepository eventoRepository;
    private final GeneradorCodigoUnico generadorCodigoUnico;
    private final CategoriaRepository categoriaRepository;
    private final RecursoRepository recursoRepository;
    private final EstadoReservaRepository estadoReservaRepository;
    private final LugarRepository lugarRepository;
    private final EtiquetaRepository etiquetaRepository;
    private final PuestoRepository puestoRepository;
  private final PlataformaRepository plataformaRepository;
  private final ReservaRepository reservaRepository;

  public AppService(EstadoEventoRepository estadoEventoRepository, DepartamentoRepository departamentoRepository, UbicacionRepository ubicacionRepository, ParticipanteRepository participanteRepository, EventoRepository eventoRepository, SocioRepository socioRespository, GeneradorCodigoUnico generadorCodigoUnico, CategoriaRepository categoriaRepository,
                    RecursoRepository recursoRepository, EstadoReservaRepository estadoReservaRepository,
                    LugarRepository lugarRepository,
                    EtiquetaRepository etiquetaRepository,
                    PuestoRepository puestoRepository,
                    PlataformaRepository plataformaRepository,
                    ReservaRepository reservaRepository) {
    this.estadoEventoRepository = estadoEventoRepository;
    this.departamentoRepository = departamentoRepository;
    this.ubicacionRepository = ubicacionRepository;
    this.participanteRepository = participanteRepository;
    this.eventoRepository = eventoRepository;
    this.socioRespository = socioRespository;
    this.generadorCodigoUnico = generadorCodigoUnico;
    this.categoriaRepository = categoriaRepository;
    this.recursoRepository = recursoRepository;
    this.estadoReservaRepository = estadoReservaRepository;
    this.lugarRepository = lugarRepository;
    this.etiquetaRepository = etiquetaRepository;
    this.puestoRepository = puestoRepository;
    this.plataformaRepository = plataformaRepository;
    this.reservaRepository = reservaRepository;
  }

  public void cargaDatos(){
    this.setTipoSocios();
    this.setEtiquetas();
    this.setRecursos();
    this.setSocios();
    this.setDepartamentos();
    this.setEstadoReserva();
    this.setUbicacion();
    this.setEventosEstados();
    this.setPlataformas();
    this.setReserva();
    this.crearEventosYParticipantes();
    this.crearEventosYParticipantes();
  }

  public void setTipoSocios(){
        // SET TIPOS DE SOCIOS
        Optional<Categoria> categoriaValidacion = categoriaRepository.findById(1L);
        if (!categoriaValidacion.isPresent()) {
            Categoria socioPlenario = new Categoria();
            socioPlenario.setNombre("socio plenario");
            Categoria socioAdherente = new Categoria();
            socioAdherente.setNombre("socio adherente");
            categoriaRepository.save(socioPlenario);
            categoriaRepository.save(socioAdherente);
        }
    }
  public void setEtiquetas(){

            Etiqueta etiqueta1 = new Etiqueta();
            etiqueta1.setNombre("Normativa");

            Etiqueta etiqueta2 = new Etiqueta();
            etiqueta2.setNombre("RRII");

            Etiqueta etiqueta3 = new Etiqueta();
            etiqueta3.setNombre("Ambiente");

            Etiqueta etiqueta4 = new Etiqueta();
            etiqueta4.setNombre("Comité Ejecutivo");

            Etiqueta etiqueta5 = new Etiqueta();
            etiqueta5.setNombre("Comisión Directiva");

            Etiqueta etiqueta6 = new Etiqueta();
            etiqueta6.setNombre("Empresas");

            Etiqueta etiqueta7 = new Etiqueta();
            etiqueta7.setNombre("Cámaras");

            Etiqueta etiqueta8 = new Etiqueta();
            etiqueta8.setNombre("Presidentes");

            Etiqueta etiqueta9 = new Etiqueta();
            etiqueta9.setNombre("Gerentes");

            Etiqueta etiqueta10 = new Etiqueta();
            etiqueta10.setNombre("Comex");

            Etiqueta etiqueta11 = new Etiqueta();
            etiqueta11.setNombre("Pyme");

            Etiqueta etiqueta12 = new Etiqueta();
            etiqueta12.setNombre("Fiscal");

            Etiqueta etiqueta13 = new Etiqueta();
            etiqueta13.setNombre("Laborales");

            Etiqueta etiqueta14 = new Etiqueta();
            etiqueta14.setNombre("Legales");

            Etiqueta etiqueta15 = new Etiqueta();
            etiqueta15.setNombre("Envases");

            etiquetaRepository.saveAll(List.of(etiqueta1, etiqueta2, etiqueta3, etiqueta4, etiqueta5, etiqueta6, etiqueta7, etiqueta8, etiqueta9, etiqueta10,etiqueta11, etiqueta12, etiqueta13, etiqueta14, etiqueta15));
    }
  public void setRecursos(){
        Recurso recurso1 = new Recurso();
        recurso1.setNombre("Proyector");

        Recurso recurso2 = new Recurso();
        recurso2.setNombre("Pizarra");

        Recurso recurso3 = new Recurso();
        recurso3.setNombre("Netbook");

        Recurso recurso4 = new Recurso();
        recurso4.setNombre("Marcadores");

        Recurso recurso5 = new Recurso();
        recurso5.setNombre("Pizzarrón Inteligente");

        Recurso recurso6 = new Recurso();
        recurso6.setNombre("Pantalla");

        recursoRepository.save(recurso1);
        recursoRepository.save(recurso2);
        recursoRepository.save(recurso3);
        recursoRepository.save(recurso4);
        recursoRepository.save(recurso5);
        recursoRepository.save(recurso6);
    }
  public void setSocios(){
            List<Categoria> categorias = categoriaRepository.findAll();
            List<Etiqueta> etiquetas = etiquetaRepository.findAll();

            Socio socio1 = new Socio("Arcor", "Pres. Arcor",12345678901L,1123232323L,"arcor@mail.com",
                categorias.get(1),"arcor.com", Date.valueOf("2023-09-21"), etiquetas.subList(0, 2));
            socioRespository.save(socio1);

            Socio socio2 = new Socio("Cento Azucarero Argentino", "Pres. CAA", 23456789012L, 1134343434L, "CAA@mail.com",
                categorias.get(0), "CAA.com", Date.valueOf("2023-09-21"), etiquetas.subList(2, 4));
            socioRespository.save(socio2);

            Socio socio3 = new Socio("CocaCola", "Pres. CocaCola", 34567890123L, 1145454545L, "cocacola@mail.com",
                categorias.get(1), "cocacola.com", Date.valueOf("2023-09-21"), etiquetas.subList(4, 6));
            socioRespository.save(socio3);

            Socio socio4 = new Socio("Ivess", "Pres. Ivess", 45678901234L, 1156565656L, "ivess@mail.com",
                categorias.get(1), "ivess.com", Date.valueOf("2023-09-21"), etiquetas.subList(6, 8));
            socioRespository.save(socio4);

            Socio socio5 = new Socio("Federación Argentina de Destilados y Aperitivos", "Pres. FADA", 56789012345L, 1167676767L, "FADA@mail.com",
                categorias.get(0), "FADA.com", Date.valueOf("2023-09-21"), etiquetas.subList(8, 10));
            socioRespository.save(socio5);

            Socio socio6 = new Socio("Paladini", "Pres. Paladini", 67890123456L, 1178787878L, "paladini@mail.com",
                categorias.get(1), "paladini.com", Date.valueOf("2023-09-21"), etiquetas.subList(10, 12));
            socioRespository.save(socio6);

            Socio socio7 = new Socio("Cámara Argentina de la Industria de Bebidas sin Alcohol", "Pres. CADIBSA", 78901234567L, 1189898989L, "CADIBSA@mail.com",
                categorias.get(0), "CADIBSA.com", Date.valueOf("2023-09-21"), etiquetas.subList(12, 14));
            socioRespository.save(socio7);

            Socio socio8 = new Socio("Cachamai", "Pres. Cachamai", 89012345678L, 1191010101L, "cachamai@mail.com",
                categorias.get(1), "cachamai.com", Date.valueOf("2023-09-21"), etiquetas.subList(14, 15));
            socioRespository.save(socio8);

            Socio socio9 = new Socio("Cámara Argentina de Especias Molineros de Pimenton y Afines", "Pres. CAEMPA", 90123456789L, 1191111111L, "CAEMPA@mail.com",
                categorias.get(0), "CAEMPA.com", Date.valueOf("2023-09-21"), etiquetas.subList(0, 5));
            socioRespository.save(socio9);

            Socio socio10 = new Socio("Fargo", "Pres. Fargo", 34567890123L, 1145454545L, "fargo@mail.com",
                categorias.get(1), "fargo.com", Date.valueOf("2023-09-21"), etiquetas.subList(5, 10));
            socioRespository.save(socio10);

            Socio socio11 = new Socio("Nestle", "Pres. Nestle", 45678901234L, 1156565656L, "nestle@mail.com",
                categorias.get(1), "nestle.com", Date.valueOf("2023-09-21"), etiquetas.subList(10, 15));
            socioRespository.save(socio11);

            Socio socio12 = new Socio("Pepsico", "Pres. Pepsico", 56789012345L, 1167676767L, "pepsico@mail.com",
                categorias.get(1), "pepsico.com", Date.valueOf("2023-09-21"), etiquetas.subList(0, 15));
            socioRespository.save(socio12);
    }
  private Departamento crearDepartamento(String nombre, String objetivo) {
    Departamento dpto = new Departamento();
    dpto.setNombre(nombre);
    dpto.setObjetivo(objetivo);
    dpto.setEstaActivo(true);
    return dpto;
  }
  private Autoridad crearAutoridad(String nombreUsuario, String nombrePuesto, PuestoRepository puestoRepository) {
    Autoridad autoridad = new Autoridad();
    autoridad.setUsuario(new Usuario(nombreUsuario));
    autoridad.setPuesto(puestoRepository.findByNombre(nombrePuesto).orElseThrow());
    return autoridad;
  }
  public void setDepartamentos(){

        List<Puesto> puestos = List.of(
            new Puesto("PRESIDENTE"),
            new Puesto("VICEPRESIDENTE"),
            new Puesto("JEFE DE DEPARTAMENTO"),
            new Puesto("SECRETARIO"),
            new Puesto("ASESOR EXTERNO"),
            new Puesto("ASISTENTE DE DEPARTAMENTO")
        );
        for (Puesto puesto : puestos) {
          if (!puestoRepository.findByNombre(puesto.getNombre()).isPresent()) {
            puestoRepository.save(puesto);
          }
        }
        
        List<Departamento> departamentos = Arrays.asList(
            crearDepartamento("Economia, Desarrollo Regional y PYME", "Su objetivo es el diseño de propuestas y seguimiento en materia de políticas de desarrollo productivo, acceso al financiamiento y mejora de la competitividad de los sectores de la industria de alimentos y bebidas, en particular las economías regionales y el entramado PyME."),
            crearDepartamento("Normativa Alimentaria", "Su objetivo principal consiste en el seguimiento y análisis de las regulaciones alimentarias vigentes y proyectos de las mismas, sean estos de carácter regional (MERCOSUR), nacional, provincial o municipal, dando cobertura a distintas instancias de discusión normativa."),
            crearDepartamento("Asuntos Institucionales y Comunicación", "Su objetivo es desarrollar la estrategia institucional y de comunicación para posicionar la agenda de propuestas de política pública para la mejora de los sectores de la IAB ante el Gobierno Nacional, los Gobiernos Provinciales y los Gobiernos Municipales."),
            crearDepartamento("Política Fiscal y Tributaria", "Su objetivo principal es analizar, evaluar y proponer las mejoras del sistema tributario que alcanza a la IAB, con intención de disminuir la carga tributaria y simplificar los regímenes correspondientes."),
            crearDepartamento("Asuntos Laborales", "Su objetivo es monitorear y analizar los temas legales y de política laboral, como así también las cuestiones relativas a la seguridad social de las empresas representadas por sus Cámaras."),
            crearDepartamento("Comercio Exterior", "Su objetivo es tener activa participación, seguimiento y monitoreo de las negociaciones económicas internacionales, en las que se encuentra involucrado el país, ya sea individualmente o como parte del MERCOSUR. Asimismo, atender la agenda de la política de internacionalización de los sectores de la IAB."),
            crearDepartamento("Sustentabilidad y Política Ambiental", "Su principal objetivo es el de atender todos aquellos temas que hacen al estudio de los planes, alternativas o proyectos de ley que tengan que ver con la gestión ambiental.")
        );

        Map<Departamento, List<Object[]>> autoridadesConfig = Map.of(
            departamentos.get(0), List.of(
                new Object[]{"Marcelo Ceretti", "PRESIDENTE"},
                new Object[]{"Alejandro Bestani", "VICEPRESIDENTE"},
                new Object[]{"Guillermo Assumma", "VICEPRESIDENTE"},
                new Object[]{"Paulina Campion_1", "JEFE DE DEPARTAMENTO"}
            ),
            departamentos.get(1), List.of(
                new Object[]{"Natalio Tassara", "PRESIDENTE"},
                new Object[]{"María Rosa Rabanal", "VICEPRESIDENTE"},
                new Object[]{"Nora Engo", "SECRETARIO"},
                new Object[]{"Abril Drach", "JEFE DE DEPARTAMENTO"}
            )
            // Continuar con los demás departamentos
        );

        autoridadesConfig.forEach((departamento, authData) -> {
            departamento.setAutoridades(
                authData.stream()
                    .map(data -> crearAutoridad((String) data[0], (String) data[1], puestoRepository))
                    .collect(Collectors.toList())
            );
        });

// 4. Guardar todos los departamentos
        departamentoRepository.saveAll(departamentos);
    }
  public void setEstadoReserva(){
            EstadoReserva estado1 = new EstadoReserva();
            estado1.setNombre("Aceptado");

            EstadoReserva estado2 = new EstadoReserva();
            estado2.setNombre("Pendiente");

            EstadoReserva estado3 = new EstadoReserva();
            estado3.setNombre("Rechazado");

            EstadoReserva estado4 = new EstadoReserva();
            estado4.setNombre("Observado");

            estadoReservaRepository.save(estado1);
            estadoReservaRepository.save(estado2);
            estadoReservaRepository.save(estado3);
            estadoReservaRepository.save(estado4);
    }
  public void setUbicacion(){
      ubicacionRepository.save(new Ubicacion("Av. Corrientes 650", "5", "San Nicolas", "Ciudad Autonoma de Buenos Aires"));
      ubicacionRepository.save(new Ubicacion("Av. 53 1025", "0", "La Plata", "Provincia de Buenos Aires"));
      ubicacionRepository.save(new Ubicacion("Blvd. San Juan 75", "15", "Córdoba", "Córdoba"));
    }
  public void setEventosEstados(){
      EstadoEvento estadoEvento1 = new EstadoEvento("Activo");
      EstadoEvento estadoEvento2 = new EstadoEvento("Suspendido");
      EstadoEvento estadoEvento3 = new EstadoEvento("Cancelado");
      EstadoEvento estadoEvento4 = new EstadoEvento("Finalizado");

      estadoEventoRepository.saveAll(List.of(estadoEvento1, estadoEvento2, estadoEvento3, estadoEvento4));
    }
  public void setPlataformas(){
      PlataformaEvento plataformaEvento1 = new PlataformaEvento("Meet");
      PlataformaEvento plataformaEvento2 = new PlataformaEvento("Teams");
      PlataformaEvento plataformaEvento3 = new PlataformaEvento("Zoom");
      PlataformaEvento plataformaEvento4 = new PlataformaEvento("Slack");
      PlataformaEvento plataformaEvento5 = new PlataformaEvento("Discord");

      plataformaRepository.saveAll(List.of(plataformaEvento1,plataformaEvento2,plataformaEvento3,plataformaEvento4,plataformaEvento5));
    }
  private long getIdAleatorio(long maxId) {
    Random random = new Random();
    return random.nextInt((int) maxId) + 1;
  }
  public void setReserva() {
      for(int i = 1; i <= 5; i++) {
          Lugar lugar = new Lugar();
          lugar.setNombre("SALA " + i);
          lugarRepository.save(lugar);
      }
      List<String> descripciones = List.of(
          "Reunión de planificación estratégica",
          "Presentación de resultados trimestrales",
          "Taller de capacitación para empleados",
          "Sesión de brainstorming de marketing",
          "Entrevista con cliente importante",
          "Revisión de proyecto de desarrollo",
          "Demostración de producto nuevo"
      );
      List<String> nombres = List.of(
          "Juan Pérez", "María García", "Carlos López",
          "Ana Martínez", "Luis Rodríguez", "Sofía Fernández", "Hernan Suaréz"
      );

      Random random = new Random();
      long maxLugares = lugarRepository.count();
      long maxEstados = estadoReservaRepository.count();
      long maxDepartamentos = departamentoRepository.count();
      long maxRecursos = recursoRepository.count();

      for(int i=0; i < 7; i++){
        // Seleccionar recursos aleatorios (entre 1 y 3 recursos)
        int numRecursos = random.nextInt((int) maxRecursos) + 1;
        List<Recurso> recursos = IntStream.range(0, numRecursos)
            .mapToObj(n -> recursoRepository.getReferenceById(getIdAleatorio(maxRecursos)))
            .collect(Collectors.toList());

        // Crear fecha y hora aleatorias dentro de los próximos 30 días
        LocalDate fecha = LocalDate.now().plusDays(random.nextInt(30));
        LocalTime horaInicio = LocalTime.of(8 + random.nextInt(10), random.nextInt(4) * 15);
        LocalTime horaFin = horaInicio.plusHours(random.nextInt(4) + 1);

        Reserva reserva = new Reserva();

        reserva.setLugar(lugarRepository.getReferenceById(getIdAleatorio(maxLugares)));
        reserva.setEstadoReserva(estadoReservaRepository.getReferenceById(getIdAleatorio(maxEstados)));
        reserva.setDepartamento(departamentoRepository.getReferenceById(getIdAleatorio(maxDepartamentos)));
        reserva.setNombre("Nombre Reservdor " + nombres.get(i));
        reserva.setEmail("email" + i + "@gmail.com");
        reserva.setDescripcion(descripciones.get(i));

        reserva.setRecursos(recursos);

        reserva.setFecha(fecha);
        reserva.setHoraInicio(horaInicio);
        reserva.setHoraFin(horaFin);

        reservaRepository.save(reserva);
      }
    }
  private void configurarEventoHibrido(Evento evento, int index, List<String> plataformas) {
    Random random = new Random();
    long maxUbicaciones = ubicacionRepository.count();
    evento.setModalidad(Modalidad.HIBRIDA);
    evento.setUbicacion(ubicacionRepository.getReferenceById((int) getIdAleatorio(maxUbicaciones)));
    evento.setDepartamento(departamentoRepository.getReferenceById(getIdAleatorio(departamentoRepository.count())));
    evento.setEstado(estadoEventoRepository.getReferenceById(3L));

    String plataforma = plataformas.get(random.nextInt(plataformas.size()));
    evento.setPlataforma(plataforma);
    evento.setLinkReunion(plataforma.toLowerCase() + "/evento-" + index);
  }
  private void configurarEventoVirtual(Evento evento, int index, List<String> plataformas, List<String> dominios) {
    Random random = new Random();
    long maxDepartamento = departamentoRepository.count();
    long maxEstados = estadoEventoRepository.count();
    evento.setModalidad(Modalidad.VIRTUAL);
    evento.setDepartamento(departamentoRepository.getReferenceById(getIdAleatorio(maxDepartamento)));
    evento.setEstado(estadoEventoRepository.getReferenceById(getIdAleatorio(maxEstados)));

    String plataforma = plataformas.get(random.nextInt(plataformas.size()));
    evento.setPlataforma(plataforma);
    evento.setLinkReunion(plataforma.toLowerCase() + "/" +
        "event-" + index + "-" +
        LocalDate.now().getMonthValue() + dominios.get(random.nextInt(dominios.size())).replace("@", ""));
  }
  private void configurarEventoPresencial(Evento evento) {
    long maxUbicaciones = ubicacionRepository.count();
    long maxDepartamento = departamentoRepository.count();
    evento.setModalidad(Modalidad.PRESENCIAL);
    evento.setUbicacion(ubicacionRepository.getReferenceById((int) getIdAleatorio(maxUbicaciones)));
    evento.setDepartamento(departamentoRepository.getReferenceById(getIdAleatorio(maxDepartamento)));
    evento.setEstado(estadoEventoRepository.getReferenceById(2L));
  }
  private List<Participante> crearParticipantesAleatorios(int cantidad, List<String> nombres,
                                                          List<String> apellidos, Socio socioAsociado, Socio socioInvitado, List<String> dominios) {
    Random random = new Random();
    return IntStream.rangeClosed(1, cantidad)
        .mapToObj(i -> {
          boolean esAsociado = random.nextBoolean();
          String nombre = nombres.get(random.nextInt(nombres.size()));
          String apellido = apellidos.get(random.nextInt(apellidos.size()));

          Participante participante = new Participante();
          participante.setNombre(nombre);
          participante.setApellido(apellido);

          if (esAsociado) {
            participante.setTipoParticipante(TipoParticipante.ASOCIADO);
            participante.setSocioAsociado(socioAsociado);
            participante.setEstado("Asociado Activo");
          } else {
            participante.setTipoParticipante(TipoParticipante.INVITADO);
            participante.setSocioConvocante(socioInvitado);
            participante.setEntidadQueRepresenta("Empresa " + apellido);
            participante.setEmail(nombre.toLowerCase() + "." + apellido.toLowerCase() +
                random.nextInt(100) + "@" + dominios.get(random.nextInt(dominios.size())));
            participante.setEstado("Invitado Confirmado");
          }
          return participanteRepository.save(participante);
        })
        .collect(Collectors.toList());
  }
  public void crearEventosYParticipantes() {

    List<String> nombresEventos = Arrays.asList(
        "Conferencia Anual", "Taller de Capacitación", "Reunión de Equipo",
        "Seminario Web", "Presentación de Producto", "Jornada de Networking",
        "Charla Inspiracional", "Workshop Técnico", "Demo Day", "Panel de Expertos"
    );

    List<String> descripcionesEventos = Arrays.asList(
        "Evento para discutir las estrategias del próximo trimestre",
        "Capacitación sobre las nuevas herramientas de la empresa",
        "Reunión para alinear los objetivos del departamento",
        "Presentación de los últimos avances tecnológicos",
        "Espacio para networking profesional entre colegas",
        "Charla motivacional con invitados especiales"
    );

    List<String> plataformas = Arrays.asList("Zoom", "Meet", "Teams", "Webex", "Discord", "BlueJeans");
    List<String> dominiosEmail = Arrays.asList("gmail.com", "empresa.com", "hotmail.com", "outlook.com");

    Socio socioAsociado = socioRespository.findById(1L).orElseThrow();
    Socio socioInvitado = socioRespository.findById(2L).orElseThrow();

    List<String> nombres = Arrays.asList("Juan", "María", "Carlos", "Ana", "Luis", "Sofía", "Pedro", "Laura");
    List<String> apellidos = Arrays.asList("Pérez", "García", "López", "Martínez", "Rodríguez", "Fernández");

    IntStream.rangeClosed(1, 20).forEach(i -> {
      Evento evento = new Evento();
      Random random = new Random();

      evento.setNombre(nombresEventos.get(random.nextInt(nombresEventos.size())) + " " + i);
      evento.setDescripcion(descripcionesEventos.get(random.nextInt(descripcionesEventos.size())));

      evento.setFechaInicio(LocalDate.now().plusDays(random.nextInt(90)));
      evento.setFechaFin(evento.getFechaInicio().plusDays(random.nextInt(3) + 1));

      LocalTime horaInicio = LocalTime.of(9 + random.nextInt(8), random.nextInt(4) * 15); // Entre 9:00 y 17:45
      evento.setHoraInicio(horaInicio);
      evento.setHoraFin(horaInicio.plusHours(random.nextInt(3) + 1)); // Duración de 1 a 4 horas

      // Configurar modalidad aleatoria con distribución diferente
      int modalidadRandom = random.nextInt(100);
      if (modalidadRandom < 15) { // 15% de probabilidad para híbrido
        configurarEventoHibrido(evento, i, plataformas);
      } else if (modalidadRandom < 65) { // 50% de probabilidad para virtual
        configurarEventoVirtual(evento, i, plataformas, dominiosEmail);
      } else { // 35% de probabilidad para presencial
        configurarEventoPresencial(evento);
      }

      // Crear participantes aleatorios (entre 2 y 8 por evento)
      int numParticipantes = 2 + random.nextInt(7);
      evento.setParticipantes(crearParticipantesAleatorios(
          numParticipantes, nombres, apellidos, socioAsociado, socioInvitado, dominiosEmail));

      // Guardar evento y generar código UUID
      Evento eventoGuardado = eventoRepository.save(evento);
      eventoGuardado.setCodigoUUID(generadorCodigoUnico.generarCodigoUnico(eventoGuardado.getId()));
      eventoRepository.save(eventoGuardado);
    });
  }
}
