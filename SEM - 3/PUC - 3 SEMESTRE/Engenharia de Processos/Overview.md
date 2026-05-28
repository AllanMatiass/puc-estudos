
**eficácia** - foca em entregar os objetivos
**eficiência** - foca em usar os recursos disponíveis de maneira otimizada

# Melhoria Contínua (ISO33000)
A "melhoria contínua" em um processo otimizado segundo a ISO/IEC 33000 refere-se a um ciclo sistemático de avaliação e aprimoramento das práticas e processos de software. Esse conceito implica em um compromisso constante com a evolução, onde as equipes buscam identificar áreas de melhoria, implementar mudanças e avaliar os resultados dessas mudanças de forma regular.

Esse processo envolve várias etapas, incluindo:

1. **Coleta de Métricas**: A obtenção de dados quantitativos e qualitativos sobre o desempenho do processo é fundamental. Isso pode incluir métricas de produtividade, qualidade, satisfação do cliente, entre outras.
    
2. **Análise de Resultados**: Após a coleta de dados, é necessário analisar as informações para identificar tendências, problemas e oportunidades de melhoria. Essa análise deve ser baseada em critérios objetivos e alinhada aos objetivos estratégicos da organização.
    
3. **Ajustes Iterativos**: Com base na análise, as equipes devem implementar ajustes nos processos. Isso pode envolver a adoção de novas práticas, ferramentas ou metodologias que possam aumentar a eficiência e a qualidade do trabalho.
    
4. **Feedback e Aprendizado**: A melhoria contínua também se baseia no feedback das partes interessadas, incluindo clientes e membros da equipe. Esse feedback é crucial para entender a eficácia das mudanças implementadas e para guiar futuras melhorias.
    
5. **Cultura de Inovação**: Promover uma cultura que valorize a inovação e a experimentação é essencial. As equipes devem ser encorajadas a testar novas abordagens e a aprender com os erros, contribuindo para um ambiente de aprendizado constante.
    
# Definição de features
A definição de funcionalidades em um produto de software é um processo que envolve a compreensão das necessidades dos usuários e a tradução dessas necessidades em características específicas do produto.

1. **Inspiração**: Este é o ponto de partida, onde se busca entender o contexto e as tendências que podem influenciar o desenvolvimento do software.
    
2. **Personas**: A partir da inspiração, criam-se personas, que são representações fictícias dos usuários finais. Elas ajudam a entender melhor quem são os usuários e quais são suas necessidades.
    
3. **Scenarios**: Com as personas definidas, os cenários são elaborados. Eles descrevem como as personas interagem com o sistema em diferentes situações, ajudando a visualizar o uso do software.
    
4. **Stories**: As histórias (ou user stories) são escritas a partir dos cenários, detalhando as funcionalidades do ponto de vista do usuário. Elas são fundamentais para capturar requisitos de forma ágil.
    
5. **Features**: Por fim, as funcionalidades (features) são definidas com base nas histórias, representando as capacidades específicas que o software deve ter para atender às necessidades dos usuários.

Esse processo é essencial para garantir que o produto final seja alinhado com as expectativas e necessidades dos usuários, promovendo uma melhor experiência e satisfação.


![[relacionarElementosISO15504.png]]

# Quais são os principais atributos de um processo que são avaliados para determinar a classificação de maturidade?

Os principais atributos de um processo que são avaliados para determinar a classificação de maturidade incluem:

1. **Gestão de Requisitos**: Avalia a capacidade de identificar, documentar e gerenciar requisitos de forma eficaz, garantindo que as necessidades dos stakeholders sejam atendidas.
    
2. **Planejamento de Projetos**: Refere-se à habilidade de planejar e estimar recursos, prazos e custos de forma precisa, permitindo uma execução mais controlada do projeto.
    
3. **Gestão de Riscos**: Envolve a identificação, análise e mitigação de riscos que podem impactar o sucesso do projeto, assegurando que medidas preventivas sejam implementadas.
    
4. **Monitoramento e Controle**: Avalia a capacidade de acompanhar o progresso do projeto e fazer ajustes conforme necessário, garantindo que os objetivos sejam alcançados dentro dos parâmetros estabelecidos.
    
5. **Qualidade do Produto**: Refere-se à implementação de práticas que asseguram a qualidade do software, incluindo testes, revisões e processos de verificação e validação.
    
6. **Capacidade de Melhoria Contínua**: Avalia a disposição e a habilidade da organização em aprender com experiências passadas e implementar melhorias nos processos.
    

Esses atributos são fundamentais para a avaliação da maturidade de um processo de software, conforme descrito em modelos como o CMMI (Capability Maturity Model Integration), que orientam as organizações na busca por processos mais eficientes e eficazes.

# De que maneira o modelo CMMI se relaciona com outras normas de qualidade de software, como a ISO 12207, em termos de avaliação e melhoria de processos?

O modelo CMMI (Capability Maturity Model Integration) e a norma ISO 12207 têm como objetivo comum a melhoria dos processos de software, mas abordam essa melhoria de maneiras diferentes.

O CMMI é um modelo de maturidade que classifica os processos em níveis de 0 a 5, permitindo que as organizações avaliem sua capacidade de processo e identifiquem áreas para melhorias. Ele se concentra em práticas específicas e em como essas práticas podem ser implementadas para alcançar um nível mais alto de maturidade. O CMMI fornece um caminho estruturado para a evolução dos processos, enfatizando a importância da gestão de processos e da melhoria contínua.

Por outro lado, a ISO 12207 é uma norma que define um conjunto de processos de ciclo de vida de software, abrangendo desde a concepção até a desativação do software. Ela fornece diretrizes e requisitos para a gestão de processos, mas não classifica os processos em níveis de maturidade como o CMMI. A ISO 12207 é mais abrangente em termos de documentação e requisitos, oferecendo um quadro que pode ser adaptado a diferentes contextos organizacionais.

Ambos os modelos podem ser complementares. Uma organização pode usar a ISO 12207 para estabelecer uma base sólida de processos e, em seguida, aplicar o CMMI para avaliar e melhorar a maturidade desses processos. A integração dos dois pode levar a uma abordagem mais robusta para a gestão da qualidade de software, permitindo que as organizações não apenas cumpram requisitos normativos, mas também melhorem continuamente suas capacidades de processo.

# Por que a estimativa por LOC pode não refletir diretamente a funcionalidade do software?

A estimativa por LOC (Lines of Code) pode não refletir diretamente a funcionalidade do software devido a vários fatores. Primeiramente, a contagem de linhas de código não considera a complexidade do que está sendo implementado. Um software com muitas linhas de código pode ser menos funcional ou eficiente do que um software mais conciso, que realiza as mesmas tarefas com menos código.

Além disso, diferentes linguagens de programação têm diferentes níveis de expressividade. Por exemplo, uma mesma funcionalidade pode ser implementada em uma linguagem de alto nível com poucas linhas de código, enquanto em uma linguagem de baixo nível pode exigir muitas mais linhas. Isso significa que a contagem de linhas pode variar significativamente dependendo da escolha da linguagem, o que pode distorcer a estimativa de esforço e custo.

Outro aspecto importante é a reutilização de código. Se um projeto utiliza bibliotecas ou frameworks que encapsulam funcionalidades complexas, a contagem de linhas de código pode não refletir o verdadeiro esforço envolvido no desenvolvimento, já que o código reutilizado não foi escrito especificamente para aquele projeto.

Por fim, a qualidade do código também influencia. Linhas de código que são mal estruturadas ou que contêm muitos bugs podem exigir mais esforço para manutenção e correção, mas ainda assim podem ser contadas como linhas de código "funcionais". Portanto, a contagem de LOC não captura adequadamente a funcionalidade real, a qualidade e a complexidade do software, tornando-a uma métrica limitada para estimativas precisas.


# Quais são os principais fatores de complexidade e produtividade que devem ser considerados ao usar a estimativa por LOC?

Ao utilizar a estimativa por LOC (Lines of Code), é fundamental considerar diversos fatores de complexidade e produtividade que podem impactar a precisão das estimativas. Entre os principais fatores estão:

1. **Complexidade do Código**: Códigos mais complexos, que envolvem lógica intricada ou múltiplas interações entre componentes, tendem a exigir mais esforço para desenvolvimento e manutenção. A complexidade pode ser avaliada através de métricas como a complexidade ciclomática.
    
2. **Experiência da Equipe**: A familiaridade da equipe com a linguagem de programação e as tecnologias utilizadas influencia diretamente a produtividade. Equipes mais experientes podem produzir código de forma mais eficiente e com menos erros.
    
3. **Qualidade do Código**: Códigos bem estruturados e documentados são mais fáceis de entender e modificar, o que pode reduzir o esforço necessário para futuras manutenções. A qualidade do código pode ser avaliada por meio de revisões de código e testes.
    
4. **Reutilização de Código**: A utilização de bibliotecas, frameworks ou componentes já existentes pode reduzir significativamente o número de linhas de código a serem escritas e, consequentemente, o esforço estimado.
    
5. **Ferramentas e Ambientes de Desenvolvimento**: O uso de ferramentas de desenvolvimento, como IDEs (Ambientes de Desenvolvimento Integrados), pode aumentar a produtividade ao fornecer recursos como autocompletar, depuração e integração contínua.
    
6. **Requisitos de Funcionalidade**: A clareza e a estabilidade dos requisitos influenciam a quantidade de código a ser escrito. Mudanças frequentes nos requisitos podem levar a um aumento no esforço e no custo.
    
7. **Normas e Padrões de Codificação**: A adesão a normas e padrões de codificação pode aumentar a legibilidade e a manutenção do código, mas também pode exigir um esforço adicional durante o desenvolvimento.


# Qual é a diferença entre as Métricas Orientadas a Função e as Métricas Orientadas ao Tamanho na estimativa de esforço?

As Métricas Orientadas a Função e as Métricas Orientadas ao Tamanho são abordagens distintas na estimativa de esforço em projetos de software, cada uma com seu foco e metodologia.

As Métricas Orientadas a Função, como os Pontos de Função, avaliam a funcionalidade do software a partir da perspectiva do usuário. Elas consideram elementos como entradas, saídas, consultas, arquivos internos e externos, permitindo uma estimativa de esforço que é independente da tecnologia ou linguagem de programação utilizada. Essa abordagem é útil para medir a complexidade funcional e a entrega de valor ao usuário final.

Por outro lado, as Métricas Orientadas ao Tamanho, como as Linhas de Código (LOC), focam na quantidade de código produzido. Essa métrica mede o tamanho do software em termos de linhas de código escritas, o que pode ser útil para estimar o esforço de desenvolvimento, mas não necessariamente reflete a funcionalidade ou a complexidade do sistema. As Métricas Orientadas ao Tamanho podem ser influenciadas por fatores como a linguagem de programação e o estilo de codificação, o que pode levar a variações na estimativa de esforço.

Em resumo, enquanto as Métricas Orientadas a Função se concentram na funcionalidade percebida pelo usuário e na entrega de valor, as Métricas Orientadas ao Tamanho se concentram na quantidade de código e podem não capturar a complexidade funcional do software de maneira tão eficaz.

# Como os fatores de ajuste utilizados nas Métricas Orientadas a Função são determinados e quais são os principais elementos considerados?

Os fatores de ajuste utilizados nas Métricas Orientadas a Função são determinados com base em características específicas do projeto e do ambiente de desenvolvimento. Esses fatores são essenciais para converter a contagem de pontos-função em estimativas de esforço mais precisas.

Os principais elementos considerados nos fatores de ajuste incluem:

1. **Complexidade do Sistema**: Avalia a complexidade técnica do software, levando em conta a quantidade de interfaces, a integração com outros sistemas e a complexidade dos dados.
    
2. **Experiência da Equipe**: Considera o nível de experiência e a familiaridade da equipe de desenvolvimento com a tecnologia e o domínio do projeto.
    
3. **Ambiente de Desenvolvimento**: Refere-se às ferramentas e tecnologias utilizadas, que podem influenciar a produtividade da equipe.
    
4. **Requisitos de Segurança e Performance**: Avalia se o sistema precisa atender a requisitos específicos de segurança, desempenho ou conformidade que possam aumentar a complexidade do desenvolvimento.
    
5. **Mudanças e Requisitos Variáveis**: Considera a possibilidade de mudanças nos requisitos durante o desenvolvimento, que podem impactar o esforço necessário.
    

Esses fatores são geralmente avaliados em uma escala de 0 a 5, onde cada fator é classificado e, em seguida, somado para gerar um fator de ajuste total. Esse fator é aplicado à contagem de pontos-função para estimar o esforço em horas-pessoa, permitindo uma previsão mais realista do tempo e recursos necessários para completar o projeto.


![[ISO12207.png]]

# Quais são as cinco grandes atividades comuns em qualquer metodologia de processo de software, segundo Roger S. Pressman?

- **Comunicação**: envolve a troca de informações entre stakeholders, garantindo entendimento claro dos requisitos e expectativas do projeto.
    
- **Planejamento**: define escopo, cronograma, recursos e riscos, estabelecendo metas e estratégias para orientar todo o desenvolvimento.
    
- **Modelagem**: cria representações abstratas (diagramas, protótipos) do sistema, facilitando a análise, design e validação antes da codificação.
    
- **Construção**: etapa de implementação e teste do software, transformando os modelos em código funcional e verificando sua qualidade.
    
- **Entrega**: inclui a implantação, documentação, treinamento e suporte, assegurando que o produto atenda aos requisitos do cliente.

![[ATIVIDADES_ROGER.png]]

![[SPICE significado.png]]

![[metricas indiretas.png]]

![[Pasted image 20260528193414.png]]

![[Pasted image 20260528193453.png]]

![[Pasted image 20260528193616.png]]

![[Pasted image 20260528193828.png]]

![[Pasted image 20260528194049.png]]

![[Pasted image 20260528194413.png|626]]


![[Pasted image 20260528194556.png]]

![[Pasted image 20260528195122.png]]

![[Pasted image 20260528195258.png]]

![[Pasted image 20260528195509.png]]

![[Pasted image 20260528200325.png]]

![[Pasted image 20260528200638.png]]

![[Pasted image 20260528200849.png]]

![[Pasted image 20260528200918.png]]



---
### Fatos:
- O modelo de avaliação ISO 33000 organiza a avaliação em duas dimensões: processo e capacidade do processo.
- A norma ISO 15504 é também conhecida como SPICE e se concentra na melhoria e determinação da capacidade dos processos de software.

### FlashCards

> [!question]- Standalone
> Software que vai rodar localmente no PC do cliente final

> [!question]- Integração Contínua
> É a prática de testar e integrar o código frequentemente ao dia

> [!question]- Processos de desenvolvimento do ISO 12207
> Análise, projeto, codificação, integração, testes e instalação

> [!question]- Ciclo de vida do ISO 12207
> Processos fundamentais, de apoio, organizacionais e de adaptação
> [!question]- Significado de KISS
> Keep it simple stupid
> Todo projeto deve ser simples, mas não simplista, para facilitar a compreensão e a manutenção.

> [!question]- Significado de KISS
> Keep it simple stupid
> Todo projeto deve ser simples, mas não simplista, para facilitar a compreensão e a manutenção.

> [!question]- Diferença entre Time Box e Não Time Box
> Time box = tempo fixo
> Não time box = tempo adequado para a quantidade de trabalho planejada

> [!question]- Feature Creep
> Adicionar funcionalidades desnecessárias sem validar se realmente precisa daquela feature

> [!question]- Ao analisar a terminologia de processo da ISO 12207, qual é a hierarquia correta que agrupa e desmembra os processos desde o nível mais alto até a menor unidade executável?
> Tipos de processos -> Processos -> Atividades -> tarefas

> [!question]- Quais são as três grandes atividades essenciais envolvidas no BDD que são convertidas nas cláusulas de um cenário?
> Dado (contexto), quando (ação/evento/trigger), então (resultado/validação)

> [!question]- A norma ISO/IEC 15504 (SPICE) introduziu o uso de PRM. O que significa PRM e qual a sua função na norma?
> Process Reference Model (Modelo de Referência de Processos) - base referencial que descreve a arquitetura e os componentes esperados de cada processo para a avaliação.