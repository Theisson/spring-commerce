import { useState } from 'react'
import { Search, Award, Sprout } from 'lucide-react'
import { mockProducts, categories } from '@/data/mock-products'

function formatPrice(cents: number): string {
  return new Intl.NumberFormat('pt-BR', {
    style: 'currency',
    currency: 'BRL',
  }).format(cents / 100)
}

export function CinematicColumns() {
  const [query, setQuery] = useState('')
  const [activeCategory, setActiveCategory] = useState('Todos')

  const previewCategories = categories.slice(0, 4)

  const filteredProducts = mockProducts
    .filter((p) => {
      const matchesQuery = p.name.toLowerCase().includes(query.toLowerCase())
      const matchesCategory = activeCategory === 'Todos' || p.category === activeCategory
      return matchesQuery && matchesCategory
    })
    .slice(0, 3)

  return (
    <section className="w-full bg-[#F5F3EE] border-y border-[#D6DDD0]">
      <div className="grid grid-cols-1 lg:grid-cols-3 divide-y lg:divide-y-0 lg:divide-x divide-[#D6DDD0]">

        {/* Coluna Esquerda — Qualidade */}
        <div className="flex flex-col items-center justify-center gap-5 px-10 py-16 text-center">
          <p className="text-[80px] lg:text-[96px] font-extrabold text-[#D6DDD0] leading-none select-none">
            Qualidade
          </p>
          <p className="text-[#7A9A80] text-[15px] leading-[1.75] max-w-[280px] font-serif">
            Milhares de produtos para todos os estilos e necessidades, com curadoria de qualidade e entrega garantida.
          </p>
          <span className="inline-flex items-center gap-2 bg-[#EBE9E3] border border-[#D6DDD0] rounded-lg px-3.5 py-2 text-[12px] font-semibold text-[#1B3A28]">
            <Sprout size={14} className="text-[#2D5E3A]" />
            Garantia de Qualidade
          </span>
        </div>

        {/* Coluna Central — Busca */}
        <div className="flex flex-col items-center justify-center gap-5 px-8 py-16 bg-[#EBE9E3]">
          <h3 className="text-xl font-bold text-[#1B3A28]">Buscar Produtos</h3>

          {/* Search input */}
          <div className="relative w-full max-w-[300px]">
            <Search size={15} className="absolute left-3.5 top-1/2 -translate-y-1/2 text-[#7A9A80]" />
            <input
              type="text"
              placeholder="Buscar por nome ou categoria..."
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              className="w-full pl-10 pr-4 py-3 rounded-2xl bg-white shadow-sm text-sm text-[#1B3A28] placeholder:text-[#7A9A80] outline-none focus:ring-2 focus:ring-[#2D5E3A]/30 transition"
            />
          </div>

          {/* Chips de categoria */}
          <div className="flex flex-wrap justify-center gap-2 w-full max-w-[300px]">
            {previewCategories.map((cat) => (
              <button
                key={cat}
                onClick={() => setActiveCategory(cat)}
                className={`text-[12px] font-medium px-3.5 py-1.5 rounded-full transition-colors duration-200 ${
                  activeCategory === cat
                    ? 'bg-[#2D5E3A] text-white'
                    : 'bg-white border border-[#D6DDD0] text-[#7A9A80] hover:border-[#2D5E3A] hover:text-[#1B3A28]'
                }`}
              >
                {cat}
              </button>
            ))}
          </div>

          {/* Mini-lista de produtos */}
          <div className="w-full max-w-[300px] flex flex-col gap-2">
            {filteredProducts.length === 0 ? (
              <p className="text-center text-[#7A9A80] text-sm py-4">Nenhum produto encontrado.</p>
            ) : (
              filteredProducts.map((product) => (
                <div
                  key={product.id}
                  className="flex items-center justify-between bg-white rounded-xl px-4 py-3 shadow-sm hover:shadow-md transition-shadow cursor-pointer"
                >
                  <span className="text-[13px] font-medium text-[#1B3A28] truncate pr-3">
                    {product.name}
                  </span>
                  <span className="text-[13px] font-bold text-[#2D5E3A] shrink-0">
                    {formatPrice(product.price)}
                  </span>
                </div>
              ))
            )}
          </div>
        </div>

        {/* Coluna Direita — Confiança */}
        <div className="flex flex-col items-center justify-center gap-5 px-10 py-16 text-center">
          <p className="text-[80px] lg:text-[96px] font-extrabold text-[#D6DDD0] leading-none select-none">
            Confiança
          </p>
          <p className="text-[#7A9A80] text-[15px] leading-[1.75] max-w-[280px] font-serif">
            Avaliados por milhares de clientes satisfeitos em todo o Brasil com compras rápidas e seguras.
          </p>
          <span className="inline-flex items-center gap-2 bg-[#2D5E3A] rounded-lg px-3.5 py-2 text-[12px] font-semibold text-white">
            <Award size={14} />
            Loja Verificada
          </span>
        </div>

      </div>
    </section>
  )
}
