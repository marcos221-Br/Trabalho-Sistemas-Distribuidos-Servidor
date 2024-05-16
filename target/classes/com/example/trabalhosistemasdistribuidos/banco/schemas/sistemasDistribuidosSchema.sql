PGDMP     -                    |            sistemasDistribuidos    15.3    15.3 "    *           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            +           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            ,           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            -           1262    25928    sistemasDistribuidos    DATABASE     �   CREATE DATABASE "sistemasDistribuidos" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';
 &   DROP DATABASE "sistemasDistribuidos";
                postgres    false            �            1259    25929 	   candidato    TABLE     �   CREATE TABLE public.candidato (
    idcandidato integer NOT NULL,
    nome character varying(30) NOT NULL,
    email character varying(50) NOT NULL,
    senha character varying(8) NOT NULL
);
    DROP TABLE public.candidato;
       public         heap    postgres    false            �            1259    25946    candidatocompetencia    TABLE     �   CREATE TABLE public.candidatocompetencia (
    "idCandidatoCompetencia" integer NOT NULL,
    "idCandidato" integer NOT NULL,
    "idCompetencia" integer NOT NULL,
    tempo integer NOT NULL
);
 (   DROP TABLE public.candidatocompetencia;
       public         heap    postgres    false            �            1259    26003    candidatovaga    TABLE     �   CREATE TABLE public.candidatovaga (
    "idCandidatoVaga" integer NOT NULL,
    "idCandidato" integer NOT NULL,
    "idVaga" integer NOT NULL,
    visualizou boolean
);
 !   DROP TABLE public.candidatovaga;
       public         heap    postgres    false            �            1259    25941    competencia    TABLE     z   CREATE TABLE public.competencia (
    "idCompetencia" integer NOT NULL,
    competencia character varying(50) NOT NULL
);
    DROP TABLE public.competencia;
       public         heap    postgres    false            �            1259    25934    empresa    TABLE     �   CREATE TABLE public.empresa (
    "idEmpresa" integer NOT NULL,
    "razaoSocial" character varying(30),
    email character varying(50) NOT NULL,
    senha integer NOT NULL,
    ramo character varying(255),
    descricao text
);
    DROP TABLE public.empresa;
       public         heap    postgres    false            �            1259    26028    idcandidato    SEQUENCE     {   CREATE SEQUENCE public.idcandidato
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999999
    CACHE 1;
 "   DROP SEQUENCE public.idcandidato;
       public          postgres    false            �            1259    25961    vaga    TABLE     �   CREATE TABLE public.vaga (
    "idVaga" integer NOT NULL,
    "idEmpresa" integer NOT NULL,
    "faixaSalarial" real,
    descricao text
);
    DROP TABLE public.vaga;
       public         heap    postgres    false            �            1259    25988    vagacompetencia    TABLE     �   CREATE TABLE public.vagacompetencia (
    "idVagaCompetencia" integer NOT NULL,
    "idVaga" integer NOT NULL,
    tempo integer NOT NULL,
    "idCompetencia" integer NOT NULL
);
 #   DROP TABLE public.vagacompetencia;
       public         heap    postgres    false                       0    25929 	   candidato 
   TABLE DATA           D   COPY public.candidato (idcandidato, nome, email, senha) FROM stdin;
    public          postgres    false    214   )       #          0    25946    candidatocompetencia 
   TABLE DATA           o   COPY public.candidatocompetencia ("idCandidatoCompetencia", "idCandidato", "idCompetencia", tempo) FROM stdin;
    public          postgres    false    217   i)       &          0    26003    candidatovaga 
   TABLE DATA           _   COPY public.candidatovaga ("idCandidatoVaga", "idCandidato", "idVaga", visualizou) FROM stdin;
    public          postgres    false    220   �)       "          0    25941    competencia 
   TABLE DATA           C   COPY public.competencia ("idCompetencia", competencia) FROM stdin;
    public          postgres    false    216   �)       !          0    25934    empresa 
   TABLE DATA           \   COPY public.empresa ("idEmpresa", "razaoSocial", email, senha, ramo, descricao) FROM stdin;
    public          postgres    false    215   �)       $          0    25961    vaga 
   TABLE DATA           Q   COPY public.vaga ("idVaga", "idEmpresa", "faixaSalarial", descricao) FROM stdin;
    public          postgres    false    218   �)       %          0    25988    vagacompetencia 
   TABLE DATA           `   COPY public.vagacompetencia ("idVagaCompetencia", "idVaga", tempo, "idCompetencia") FROM stdin;
    public          postgres    false    219   �)       .           0    0    idcandidato    SEQUENCE SET     :   SELECT pg_catalog.setval('public.idcandidato', 1, false);
          public          postgres    false    221            �           2606    25950 .   candidatocompetencia CandidatoCompetencia_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY public.candidatocompetencia
    ADD CONSTRAINT "CandidatoCompetencia_pkey" PRIMARY KEY ("idCandidatoCompetencia");
 Z   ALTER TABLE ONLY public.candidatocompetencia DROP CONSTRAINT "CandidatoCompetencia_pkey";
       public            postgres    false    217            �           2606    26007     candidatovaga CandidatoVaga_pkey 
   CONSTRAINT     o   ALTER TABLE ONLY public.candidatovaga
    ADD CONSTRAINT "CandidatoVaga_pkey" PRIMARY KEY ("idCandidatoVaga");
 L   ALTER TABLE ONLY public.candidatovaga DROP CONSTRAINT "CandidatoVaga_pkey";
       public            postgres    false    220            ~           2606    25933    candidato Candidato_pkey 
   CONSTRAINT     a   ALTER TABLE ONLY public.candidato
    ADD CONSTRAINT "Candidato_pkey" PRIMARY KEY (idcandidato);
 D   ALTER TABLE ONLY public.candidato DROP CONSTRAINT "Candidato_pkey";
       public            postgres    false    214            �           2606    25945    competencia Competencia_pkey 
   CONSTRAINT     i   ALTER TABLE ONLY public.competencia
    ADD CONSTRAINT "Competencia_pkey" PRIMARY KEY ("idCompetencia");
 H   ALTER TABLE ONLY public.competencia DROP CONSTRAINT "Competencia_pkey";
       public            postgres    false    216            �           2606    25940    empresa Empresa_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.empresa
    ADD CONSTRAINT "Empresa_pkey" PRIMARY KEY ("idEmpresa");
 @   ALTER TABLE ONLY public.empresa DROP CONSTRAINT "Empresa_pkey";
       public            postgres    false    215            �           2606    25992 $   vagacompetencia VagaCompetencia_pkey 
   CONSTRAINT     u   ALTER TABLE ONLY public.vagacompetencia
    ADD CONSTRAINT "VagaCompetencia_pkey" PRIMARY KEY ("idVagaCompetencia");
 P   ALTER TABLE ONLY public.vagacompetencia DROP CONSTRAINT "VagaCompetencia_pkey";
       public            postgres    false    219            �           2606    25967    vaga Vaga_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.vaga
    ADD CONSTRAINT "Vaga_pkey" PRIMARY KEY ("idVaga");
 :   ALTER TABLE ONLY public.vaga DROP CONSTRAINT "Vaga_pkey";
       public            postgres    false    218            �           2606    25951     candidatocompetencia idCandidato    FK CONSTRAINT     �   ALTER TABLE ONLY public.candidatocompetencia
    ADD CONSTRAINT "idCandidato" FOREIGN KEY ("idCandidato") REFERENCES public.candidato(idcandidato);
 L   ALTER TABLE ONLY public.candidatocompetencia DROP CONSTRAINT "idCandidato";
       public          postgres    false    217    3198    214            �           2606    26008    candidatovaga idCandidato    FK CONSTRAINT     �   ALTER TABLE ONLY public.candidatovaga
    ADD CONSTRAINT "idCandidato" FOREIGN KEY ("idCandidato") REFERENCES public.candidato(idcandidato);
 E   ALTER TABLE ONLY public.candidatovaga DROP CONSTRAINT "idCandidato";
       public          postgres    false    220    214    3198            �           2606    25956 "   candidatocompetencia idCompetencia    FK CONSTRAINT     �   ALTER TABLE ONLY public.candidatocompetencia
    ADD CONSTRAINT "idCompetencia" FOREIGN KEY ("idCompetencia") REFERENCES public.competencia("idCompetencia");
 N   ALTER TABLE ONLY public.candidatocompetencia DROP CONSTRAINT "idCompetencia";
       public          postgres    false    217    216    3202            �           2606    25998    vagacompetencia idCompetencia    FK CONSTRAINT     �   ALTER TABLE ONLY public.vagacompetencia
    ADD CONSTRAINT "idCompetencia" FOREIGN KEY ("idCompetencia") REFERENCES public.competencia("idCompetencia");
 I   ALTER TABLE ONLY public.vagacompetencia DROP CONSTRAINT "idCompetencia";
       public          postgres    false    216    219    3202            �           2606    25968    vaga idEmpresa    FK CONSTRAINT     ~   ALTER TABLE ONLY public.vaga
    ADD CONSTRAINT "idEmpresa" FOREIGN KEY ("idEmpresa") REFERENCES public.empresa("idEmpresa");
 :   ALTER TABLE ONLY public.vaga DROP CONSTRAINT "idEmpresa";
       public          postgres    false    215    3200    218            �           2606    25993    vagacompetencia idVaga    FK CONSTRAINT     }   ALTER TABLE ONLY public.vagacompetencia
    ADD CONSTRAINT "idVaga" FOREIGN KEY ("idVaga") REFERENCES public.vaga("idVaga");
 B   ALTER TABLE ONLY public.vagacompetencia DROP CONSTRAINT "idVaga";
       public          postgres    false    3206    218    219            �           2606    26013    candidatovaga idVaga    FK CONSTRAINT     {   ALTER TABLE ONLY public.candidatovaga
    ADD CONSTRAINT "idVaga" FOREIGN KEY ("idVaga") REFERENCES public.vaga("idVaga");
 @   ALTER TABLE ONLY public.candidatovaga DROP CONSTRAINT "idVaga";
       public          postgres    false    218    220    3206                L   x�3��M,J�/Vp��3�JRs3��s3s���s9-Ľ�-��9RS�򳡔\������W� q�      #      x������ � �      &      x������ � �      "      x������ � �      !      x������ � �      $      x������ � �      %      x������ � �     