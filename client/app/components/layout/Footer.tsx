import { Leaf, ShieldCheck, RotateCcw } from 'lucide-react'

const footerLinks = {
  loja: {
    title: 'Loja',
    links: ['Catálogo', 'Promoções', 'Lançamentos', 'Mais Vendidos'],
  },
  conta: {
    title: 'Minha Conta',
    links: ['Meus Pedidos', 'Perfil', 'Carteira Digital', 'Endereços'],
  },
  suporte: {
    title: 'Suporte',
    links: ['Central de Ajuda', 'Política de Devolução', 'Fale Conosco'],
  },
}

export function Footer() {
  return (
    <footer className="bg-[#1B3A28] text-white">
      <div className="max-w-screen-xl mx-auto px-6 lg:px-16 py-14">

        {/* Topo */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-10 lg:gap-16 mb-10">

          {/* Marca */}
          <div className="flex flex-col gap-5">
            <a href="#" className="flex items-center gap-2">
              <Leaf size={22} className="text-[#7A9A80]" strokeWidth={2.5} />
              <span className="text-white font-bold text-lg tracking-tight">SpringCommerce</span>
            </a>
            <p className="text-[#ffffff99] text-sm leading-relaxed font-serif max-w-[260px]">
              Sua loja online com os melhores produtos e as melhores condições de pagamento.
            </p>
          </div>

          {/* Colunas de links */}
          {Object.values(footerLinks).map((col) => (
            <div key={col.title} className="flex flex-col gap-4">
              <h4 className="text-sm font-semibold text-white">{col.title}</h4>
              <ul className="flex flex-col gap-3">
                {col.links.map((link) => (
                  <li key={link}>
                    <a
                      href="#"
                      className="text-[13px] text-[#ffffff99] hover:text-white transition-colors duration-200"
                    >
                      {link}
                    </a>
                  </li>
                ))}
              </ul>
            </div>
          ))}
        </div>

        {/* Divisor */}
        <div className="border-t border-[#ffffff15] pt-6 flex flex-col sm:flex-row items-center justify-between gap-4">
          <p className="text-[13px] text-[#ffffff66]">
            © 2026 SpringCommerce. Todos os direitos reservados.
          </p>

          {/* Badges */}
          <div className="flex items-center gap-3">
            <span className="flex items-center gap-1.5 bg-white/10 rounded-md px-3 py-1.5 text-[12px] text-[#ffffff99]">
              <ShieldCheck size={13} className="text-[#7A9A80]" />
              Compra Segura
            </span>
            <span className="flex items-center gap-1.5 bg-white/10 rounded-md px-3 py-1.5 text-[12px] text-[#ffffff99]">
              <RotateCcw size={13} className="text-[#7A9A80]" />
              30 dias de garantia
            </span>
          </div>
        </div>
      </div>
    </footer>
  )
}
