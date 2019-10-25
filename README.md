
# EvolvingRobots

A framework for evolutionary robotics for Coppellia Robotics' V-REP http://www.coppeliarobotics.com/

This project was supported by CNPq and UEFS.

# Estrutura Geral
O simulador conta com uma estrutura baseada no modelo "Cliente - Servidor", onde o Framework funciona como um cliente que se conecta a diversos servidores (Simulador V-REP).

Como já deve ser esperado existem, duas partes a serem configuradas, e serão descritas a seguir.

## Configuração no Simulador (V-REP)
Primeiramente, vale ressaltar que o for descrito abaixo deve ser feito no controlador da simulação que deve se tratar do script atrelado ao objeto de nome **"Controller"**

1. Configurar conexão: "simRemoteApi.start(porta, tamanho do pacote, debug e pre-enable)" por padrão o comando é: `simRemoteApi.start(8001, 1300, true, false)

A partir da conexão estabelecida com sucesso são chamados dois métodos que devem ser definidos no **"Controller"**:

1.  *startSimulation()* -> Código responsável por iniciar a simulação e fazer a configuração do indivíduo com base na string recebida pela chamada da API (String deve ser definida no método toString da representação no framework)
2. *getSimulationFitness()* -> responsável por retornar o fitness do indivíduo, deve retornar um float na segunda posição do array de retorno com o fitness do indivíduo.

Deve também existir uma função como a seguinte, alterando apenas a porta de coneção caso necessário.

```lua
function disableServer ()
	simRemoteApi.stop(8001)
end
```

## Configuração no Framework (JAVA)

No Framework, a lógica para evolução está pronta, bem como a conexão com o simulador, envio de dados e demais etapas internas da do processo de evolução e comunicação. São disponibilizados alguns métodos abertos para customização afim de adequar partes do algoritmo evolutivo com cada experimento.

O Framework contém os seguintes métodos/funções para controle da evolução:

 - Representação
 - Inicialização da população
 - Seleção da população
 - Reprodução
 - Mutação
 - Geração de nova população
 - Controle dos dados da evolução*

### Classe inicial:
Definida pela classe: **model/"Evolve"**, contempla a inicialização da população e o ciclo principal da evolução. Tem como método principal o "Start".
> Nesse item não é esperada nenhuma modificação, exceto pela lógica de geração de uma nova população (seguindo tutorial abaixo)

### Representação:
Definida pela classe **representation/"IntegerArrayRep"**, contempla o modelo de representação utilizado na simulação, e já vem implementada com um array de inteiros como modelo. 
Essa classe implementa a interface *util/"Representation"*. No caso de modificação, seguir atento aos métodos implementados pois todos devem ser adaptados para o novo modelo de dados a ser usado como representação.

### Inicialização da população:
A regra para inicialização está como parte do modelo de inicialização, onde, no método **randonInit()** é fornecido um valor como "Margem" que pode ser utilizado ou não. Ali deve ser feita uma inicialização do modelo de dados utilizado para cada indivíduo, visto que na inicialização da população esse método será chamado em todos os indivíduos.

### Seleção da população e Mutação:
A seleção da população é feita primeiramente ordenando os indivíduos em ordem decrescente, usando como base o valor de fitness de cada um após a simulação.
**Essa é a unica parte que se admite alteração no LOOP principal da evolução na classe principal explicada acima**
A seleção de indivíduos deve ser feita e os mesmos devem ser adicionados ao array *"nextGenerationAgents". **Linha 146 até 185** 
> Vale ressaltar que deve ser feita a seleção, montagem dos novos indivíduos e aplicada a mutação, os indivíduos presentes no array nextGenerationAgents serão utilizados para as próximas simulações.


# Interface
