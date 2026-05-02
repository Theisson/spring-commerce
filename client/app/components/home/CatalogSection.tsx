import { useState } from 'react'
import { ArrowRight, ChevronLeft, ChevronRight } from 'lucide-react'
import { ProductCard } from '@/components/ui/ProductCard'
import { mockProducts, categories } from '@/data/mock-products'

const ITEMS_PER_PAGE = 8
const TOTAL_PAGES = 3

export function CatalogSection() {
  const [activeCategory, setActiveCategory] = useState('Todos')
  const [currentPage, setCurrentPage] = useState(1)

  const filtered = mockProducts.filter(
    (p) => activeCategory === 'Todos' || p.category === activeCategory
  )

  const paginated = filtered.slice(0, ITEMS_PER_PAGE)

  function handleCategoryChange(cat: string) {
    setActiveCategory(cat)
    setCurrentPage(1)
  }

  return (
    <section id="catalog" className="bg-[#F5F3EE] py-16 lg:py-20">
      <div className="max-w-screen-xl mx-auto px-6 lg:px-16 flex flex-col gap-8">

        {/* Cabeçalho */}
        <div className="flex items-end justify-between gap-4">
          <div className="flex flex-col gap-1.5">
            <span className="text-[11px] font-bold text-[#2D5E3A] uppercase tracking-[2px]">
              Nossos Produtos
            </span>
            <h2 className="text-3xl lg:text-4xl font-bold text-[#1B3A28] leading-tight">
              Catálogo
            </h2>
          </div>
          <button className="hidden sm:flex items-center gap-2 border border-[#D6DDD0] text-[#1B3A28] hover:bg-[#EBE9E3] text-sm font-medium px-5 py-2.5 rounded-xl transition-colors duration-200 shrink-0">
            Ver todos
            <ArrowRight size={14} />
          </button>
        </div>

        {/* Filtros */}
        <div className="flex items-center gap-2 overflow-x-auto pb-1 scrollbar-none">
          {categories.map((cat) => (
            <button
              key={cat}
              onClick={() => handleCategoryChange(cat)}
              className={`whitespace-nowrap text-[13px] font-medium px-4 py-2 rounded-full border transition-colors duration-200 ${
                activeCategory === cat
                  ? 'bg-[#2D5E3A] text-white border-[#2D5E3A]'
                  : 'bg-white border-[#D6DDD0] text-[#7A9A80] hover:border-[#2D5E3A] hover:text-[#1B3A28]'
              }`}
            >
              {cat}
            </button>
          ))}
        </div>

        {/* Grid de produtos */}
        {paginated.length === 0 ? (
          <div className="text-center py-16 text-[#7A9A80]">
            <p className="text-lg font-medium">Nenhum produto nesta categoria.</p>
          </div>
        ) : (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-5 lg:gap-6">
            {paginated.map((product) => (
              <ProductCard key={product.id} product={product} />
            ))}
          </div>
        )}

        {/* Paginação */}
        <div className="flex items-center justify-center gap-2 pt-2">
          <button
            onClick={() => setCurrentPage((p) => Math.max(1, p - 1))}
            disabled={currentPage === 1}
            className="w-9 h-9 flex items-center justify-center rounded-xl border border-[#D6DDD0] bg-white text-[#7A9A80] hover:bg-[#EBE9E3] hover:text-[#1B3A28] disabled:opacity-40 disabled:cursor-not-allowed transition-colors"
          >
            <ChevronLeft size={16} />
          </button>

          {Array.from({ length: TOTAL_PAGES }).map((_, i) => {
            const page = i + 1
            return (
              <button
                key={page}
                onClick={() => setCurrentPage(page)}
                className={`w-9 h-9 flex items-center justify-center rounded-xl text-sm font-medium transition-colors ${
                  currentPage === page
                    ? 'bg-[#2D5E3A] text-white'
                    : 'border border-[#D6DDD0] bg-white text-[#7A9A80] hover:bg-[#EBE9E3] hover:text-[#1B3A28]'
                }`}
              >
                {page}
              </button>
            )
          })}

          <button
            onClick={() => setCurrentPage((p) => Math.min(TOTAL_PAGES, p + 1))}
            disabled={currentPage === TOTAL_PAGES}
            className="w-9 h-9 flex items-center justify-center rounded-xl border border-[#D6DDD0] bg-white text-[#7A9A80] hover:bg-[#EBE9E3] hover:text-[#1B3A28] disabled:opacity-40 disabled:cursor-not-allowed transition-colors"
          >
            <ChevronRight size={16} />
          </button>
        </div>

      </div>
    </section>
  )
}
