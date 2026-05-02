import { ArrowRight, ShoppingBag } from 'lucide-react'

export function HeroSection() {
  return (
    <section className="relative w-full min-h-[680px] lg:min-h-[780px] overflow-hidden bg-[#1B3A28] flex items-center">

      {/* Imagem de fundo */}
      <div className="absolute inset-0">
        <img
          src="https://images.unsplash.com/photo-1448375240586-882707db888b?w=1600&q=80"
          alt="Floresta exuberante"
          className="w-full h-full object-cover opacity-40"
        />
        {/* Gradiente sobre a imagem */}
        <div className="absolute inset-0 bg-gradient-to-t from-[#1B3A28] via-[#1B3A28]/70 to-[#1B3A28]/20" />
      </div>

      {/* Conteúdo central */}
      <div className="relative z-10 w-full max-w-screen-xl mx-auto px-6 lg:px-16 py-24 flex flex-col items-center text-center gap-6">

        {/* Eyebrow */}
        <div className="flex items-center gap-2 bg-white/10 backdrop-blur-sm border border-white/20 rounded-full px-4 py-2">
          <span className="w-2 h-2 rounded-full bg-[#7A9A80] animate-pulse" />
          <span className="text-white/80 text-xs font-medium tracking-wide">Sua loja online</span>
        </div>

        {/* Título */}
        <h1 className="text-4xl sm:text-5xl lg:text-7xl font-extrabold text-white leading-[1.08] max-w-4xl">
          Tudo o que você{' '}
          <br className="hidden sm:block" />
          precisa, em um lugar
        </h1>

        {/* Subtítulo */}
        <p className="text-white/75 text-lg sm:text-xl max-w-xl leading-relaxed font-serif">
          Encontre os melhores produtos com preços competitivos. Entrega rápida e segura para todo o Brasil.
        </p>

        {/* CTAs */}
        <div className="flex flex-col sm:flex-row items-center gap-3 mt-2">
          <a
            href="#catalog"
            className="flex items-center gap-2 bg-[#2D5E3A] hover:bg-[#3a7548] text-white font-semibold text-sm px-7 py-3.5 rounded-xl transition-colors duration-200"
          >
            <ShoppingBag size={17} />
            Explorar Catálogo
          </a>
          <a
            href="#"
            className="flex items-center gap-2 text-white/85 hover:text-white border border-white/25 hover:border-white/50 font-medium text-sm px-7 py-3.5 rounded-xl transition-all duration-200 backdrop-blur-sm"
          >
            Ver Ofertas
            <ArrowRight size={15} />
          </a>
        </div>

        {/* Indicadores de credibilidade */}
        <div className="flex flex-wrap justify-center items-center gap-6 mt-6 pt-6 border-t border-white/10 w-full max-w-lg">
          {[
            { value: '50k+', label: 'Produtos' },
            { value: '98%', label: 'Satisfação' },
            { value: '24h', label: 'Suporte' },
          ].map((stat) => (
            <div key={stat.label} className="text-center">
              <p className="text-white font-bold text-xl">{stat.value}</p>
              <p className="text-white/60 text-xs mt-0.5">{stat.label}</p>
            </div>
          ))}
        </div>
      </div>

      {/* Onda de transição para o stats bar */}
      <div className="absolute bottom-0 left-0 right-0 h-12 bg-gradient-to-t from-[#1B3A28] to-transparent" />
    </section>
  )
}
