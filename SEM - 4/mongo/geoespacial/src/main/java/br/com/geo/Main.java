package br.com.geo;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final double RAIO_TERRA_KM = 6371.0;

    public static void main(String[] args) {
        System.out.println("PONTOS GEOGRÁFICOS");

        try (MongoService service = new MongoService();
             Scanner scanner = new Scanner(System.in)) {

            boolean running = true;
            while (running) {
                System.out.println("\n----------------- MENU PRINCIPAL -----------------");
                System.out.println("1. Cadastrar novo ponto");
                System.out.println("2. Listar pontos cadastrados");
                System.out.println("3. Calcular distância entre 2 pontos");
                System.out.println("4. Pegar pontos próximos");
                System.out.println("0. Sair");
                System.out.print("Escolha uma opção: ");

                String opcao = scanner.nextLine().trim();

                switch (opcao) {
                    case "1" -> cadastrarPonto(service, scanner);
                    case "2" -> listarPontos(service);
                    case "3" -> calcularDistancia(service, scanner);
                    case "4" -> pegarPontosProximos(service, scanner);

                    case "0" -> {
                        System.out.println("Encerrando o programa... Até logo!");
                        running = false;
                    }
                    default -> System.out.println("Opção inválida! Tente novamente.");
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao conectar ou executar no MongoDB: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void pegarPontosProximos(MongoService service, Scanner scanner) {
        Ponto ponto = getPonto(service, scanner);
        if (ponto == null) {
            System.out.println("Ponto não encontrado.");
            return;
        }


        boolean error;
        int km = 5000;

        do{
            error = false;
            try{
                System.out.println("Digite a quantidade de quilometros: ");
                km = readInt(scanner) * 1000;
            } catch (Exception e){
                error = true;
            }
        } while(error);

        List<Ponto> pontos = buscarProximos(service, new Coordenadas(ponto), km);

        if (pontos.isEmpty()) System.out.println("Não há pontos próximos");

        System.out.println("LAT | LON | DISTÂNCIA");
        for(Ponto p : pontos){
            if (p.getId() == ponto.getId()) continue;

            System.out.printf("Ponto %s: %.2f | %.2f | %.2f\n",
                    p.getNome(), p.getLatitude(), p.getLongitude(),
                    calcularHaversineKm(new Coordenadas(ponto), new Coordenadas(p))
            );
        }
        return;
    }

    private static void cadastrarPonto(MongoService service, Scanner scanner) {
        System.out.println("\n--- CADASTRO DE PONTO ---");

        System.out.print("Informe o nome/identificador do local: ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) {
            System.out.println("Erro: O nome não pode ser vazio.");
            return;
        }

        Double latitude = readLocation(scanner, "Latitude (-90 a 90): ", -90.0, 90.0);
        if (latitude == null) return;

        Double longitude = readLocation(scanner, "Longitude (-180 a 180): ", -180.0, 180.0);
        if (longitude == null) return;

        Ponto ponto = service.cadastrarPonto(nome, latitude, longitude);
        System.out.println("\n>>> Ponto cadastrado com sucesso! <<<");
        System.out.printf("ID Sequencial: %d | Nome: %s | Latitude: %.6f | Longitude: %.6f%n",
                ponto.getId(), ponto.getNome(), ponto.getLatitude(), ponto.getLongitude());
    }


    private static List<Ponto> buscarProximos(MongoService service, Coordenadas coordenadas, int metros){
        return service.buscarPontosProximos(coordenadas, metros);
    }

    private static void listarPontos(MongoService service) {
        System.out.println("\n--- PONTOS CADASTRADOS ---");
        List<Ponto> pontos = service.listarTodos();

        if (pontos.isEmpty()) {
            System.out.println("Nenhum ponto cadastrado até o momento.");
            return;
        }

        System.out.printf("%-6s | %-25s | %-15s | %-15s%n", "ID", "NOME", "LATITUDE", "LONGITUDE");
        System.out.println("------------------------------------------------------------------");
        for (Ponto p : pontos) {
            System.out.printf("%-6d | %-25s | %-15.6f | %-15.6f%n",
                    p.getId(), p.getNome(), p.getLatitude(), p.getLongitude());
        }
        System.out.println("Total de pontos: " + pontos.size());
    }

    private static Ponto getPonto(MongoService mongoService, Scanner scanner){

        System.out.print("Digite o ID do ponto: ");
        Integer id1 = readInt(scanner);
        if (id1 == null) return null;

        Ponto ponto1 = mongoService.findById(id1);
        if (ponto1 == null) {
            System.out.printf("Erro: Ponto com ID %d NÃO está cadastrado no banco de dados!%n", id1);
            return null;
        }

        return ponto1;
    }

    private static void calcularDistancia(MongoService mongoService, Scanner scanner) {
        System.out.println("\n--- CÁLCULO DE DISTÂNCIA ENTRE 2 PONTOS ---");

        System.out.print("Digite o ID do primeiro ponto: ");
        Integer id1 = readInt(scanner);
        if (id1 == null) return;

        System.out.print("Digite o ID do segundo ponto: ");
        Integer id2 = readInt(scanner);
        if (id2 == null) return;

        Ponto ponto1 = mongoService.findById(id1);
        if (ponto1 == null) {
            System.out.printf("Erro: Ponto com ID %d NÃO está cadastrado no banco de dados!%n", id1);
            return;
        }

        Ponto ponto2 = mongoService.findById(id2);
        if (ponto2 == null) {
            System.out.printf("Erro: Ponto com ID %d NÃO está cadastrado no banco de dados!%n", id2);
            return;
        }

        if (id1.equals(id2)) {
            System.out.println("Aviso: Você selecionou o mesmo ponto para origem e destino.");
        }

        double distanciaKm = calcularHaversineKm(
                new Coordenadas(ponto1),
                new Coordenadas(ponto2)
        );

        double distanciaMetros = distanciaKm * 1000.0;

        System.out.println("\n================ RESULTADO ================");
        System.out.printf("Ponto 1: [ID %d] %s (Lat: %.6f, Lon: %.6f)%n",
                ponto1.getId(), ponto1.getNome(), ponto1.getLatitude(), ponto1.getLongitude());
        System.out.printf("Ponto 2: [ID %d] %s (Lat: %.6f, Lon: %.6f)%n",
                ponto2.getId(), ponto2.getNome(), ponto2.getLatitude(), ponto2.getLongitude());
        System.out.println("-------------------------------------------");
        System.out.printf("Distância em quilômetros: %.4f km%n", distanciaKm);
        System.out.printf("Distância em metros:      %.2f m%n", distanciaMetros);
        System.out.println("===========================================");
    }

    /**
     * Calcula a distância entre duas coordenadas pela fórmula de Haversine (em km).
     */
    public static double calcularHaversineKm(Coordenadas coordenadas1, Coordenadas coordenadas2) {
        double lon1 = coordenadas1.lon();
        double lon2 = coordenadas2.lon();
        double lat1 = coordenadas1.lat();
        double lat2 = coordenadas2.lat();

        // Calcuka a diferença angular
        double diffLat = Math.toRadians(lat2 - lat1);
        double diffLon = Math.toRadians(lon2 - lon1);

        // transforma as latitudes em radiano
        double radLat1 = Math.toRadians(lat1);
        double radLat2 = Math.toRadians(lat2);


        // calcula o "a"
        double powDiffLat = Math.pow(
                Math.sin(diffLat / 2), 2
        );

        double powDiffLon = Math.pow(
                Math.sin(diffLon / 2), 2
        );

        double a = powDiffLat + Math.cos(radLat1) * Math.cos(radLat2) * powDiffLon;

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RAIO_TERRA_KM * c;
    }

    private static Double readLocation(Scanner scanner, String msg, double min, double max) {
        while (true) {
            System.out.print(msg);
            String input = scanner.nextLine().trim().replace(",", ".");
            if (input.equalsIgnoreCase("cancelar")) {
                System.out.println("Operação cancelada.");
                return null;
            }

            try {
                double valor = Double.parseDouble(input);
                if (valor < min || valor > max) {
                    System.out.printf("Valor fora do intervalo permitido (%.1f a %.1f). Tente novamente ou digite 'cancelar'.%n", min, max);
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Número inválido! Use formato decimal (ex: -19.9234). Tente novamente ou digite 'cancelar'.");
            }
        }
    }

    private static Integer readInt(Scanner scanner) {
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido! Digite um número inteiro.");
            return null;
        }
    }
}
