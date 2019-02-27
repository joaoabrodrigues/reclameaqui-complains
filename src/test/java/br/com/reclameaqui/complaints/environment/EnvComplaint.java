package br.com.reclameaqui.complaints.environment;

import br.com.reclameaqui.complaints.model.Company;
import br.com.reclameaqui.complaints.model.Complaint;
import br.com.reclameaqui.complaints.model.Locale;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnvComplaint {

    private static final String COUNTRY_BRASIL = "Brasil";

    public static Complaint createComplaintTicketServicos() {
        return Complaint.builder()
                .title("Preciso mudar a senha!")
                .description("Quero mudar a senha do cartão, mais pra isso precisa do número da matrícula, o número da matrícula da errado, acredito que tenha mais alguns números antes do número da matrícula!")
                .locale(Locale.builder()
                        .address("Avenida 1, 1")
                        .city("Nossa Senhora do Socorro")
                        .zipCode("49160-000")
                        .state("Sergipe")
                        .country(COUNTRY_BRASIL)
                        .build())
                .company(Company.builder()
                        .name("Ticket Serviços")
                        .build())
                .lat("-53.2222763")
                .lng("-27.3156237")
                .build();
    }

    public static Complaint createComplaintBancoItau() {
        return Complaint.builder()
                .title("Cobrança de tarifas")
                .description("Fiz a solicitação da tarifa zerada, conforme normas do banco central, tinha um débito de tarifas")
                .locale(Locale.builder()
                        .address("Avenida Paulista, 2200")
                        .city("São Paulo")
                        .zipCode("01310-300")
                        .state("São Paulo")
                        .country(COUNTRY_BRASIL)
                        .build())
                .company(Company.builder()
                        .name("Banco Itaú")
                        .build())
                .lat("-12.2222763")
                .lng("-44.3156237")
                .build();
    }

    public static Complaint createComplaintVivo() {
        return Complaint.builder()
                .title("Aumento do valor do plano")
                .description("O valor do meu plano aumentou em 50 reais sem nenhum aviso de reajuste")
                .locale(Locale.builder()
                        .address("Avenida Farrapos, 1267")
                        .city("Porto Alegre")
                        .zipCode("90220-004")
                        .state("Rio Grande do Sul")
                        .country(COUNTRY_BRASIL)
                        .build())
                .company(Company.builder()
                        .name("Vivo (Celular, Fixo, Internet, TV)")
                        .build())
                .lat("-12.2222763")
                .lng("-44.3156237")
                .build();
    }
}
