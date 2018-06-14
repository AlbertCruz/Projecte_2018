-- CONSULTA CLASSIFICACIONS
select g.id, p.guanyador, p.data_partida, p.mode_victoria, p.carambolesA, p.carambolesB, s1.nom as 'nomA', 
s1.cognom1 as 'cognom1A', s2.nom as 'nomB', s2.cognom1 as 'cognom1B', g.descripcio as 'descGrup', t.data_inici, 
t.nom, m.descripcio as 'descModalitat'
from partides p
left join grup g on p.grup_id = g.id
left join soci s1 on p.soci_a = s1.id
left join soci s2 on p.soci_b = s2.id
left join torneig t on p.torneig_id = t.id
left join modalitat m on t.modalitat_id = m.id
where t.actiu = 1
and t.preinscripcio_oberta = 0
and g.actiu = 1
and p.estat_partida = 2
and t.id=$P{torneigId}

-- CONSULTA SOCIS
select s.id, s.nom, s.nif, s.cognom1, s.cognom2, p.soci_a as 'idSociA', p.soci_b as 'idSociB', p.carambolesA, 
p.carambolesB, p.data_partida, p.num_entrades, p.mode_victoria, 
(select count(*) from partides paux where (paux.guanyador=1 and paux.soci_a=s.id) or (paux.guanyador=2 and paux.soci_b=s.id)) as 'QT_Victories', 
t.nom as 'torneigNom', t.data_inici as 'torneigDataIni', t.data_final as 'torneigDataFin', m.descripcio as 'Mod_Torneig'
from soci s 
left join partida p on p.soci_a = s.id or p.soci_b = s.id
left join torneig t on p.torneig_id = t.id
left join modalitat m on t.modalitat_id = m.id
where p.estat_partida=2
order by QT_Victories desc
