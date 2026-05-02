import { ShoppingCart, Star } from 'lucide-react'
import type { Product } from '@/types/product'

interface ProductCardProps {
  product: Product
}

function formatPrice(cents: number): string {
  return new Intl.NumberFormat('pt-BR', {
    style: 'currency',
    currency: 'BRL',
  }).format(cents / 100)
}

export function ProductCard({ product }: ProductCardProps) {
  return (
    <div className="group bg-white rounded-2xl overflow-hidden shadow-[0_2px_4px_rgba(0,0,0,0.03),0_8px_24px_rgba(0,0,0,0.06)] hover:shadow-[0_4px_12px_rgba(0,0,0,0.08),0_16px_40px_rgba(0,0,0,0.1)] transition-shadow duration-300 flex flex-col">
      {/* Imagem */}
      <div className="relative aspect-[4/3] overflow-hidden bg-[#EBE9E3]">
        <img
          src={product.imageUrl}
          alt={product.name}
          className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500"
        />
        {product.badge && (
          <span className="absolute top-3 left-3 bg-[#2D5E3A] text-white text-[11px] font-semibold px-2.5 py-1 rounded-full">
            {product.badge}
          </span>
        )}
        <button
          className="absolute bottom-3 right-3 w-9 h-9 bg-white rounded-xl flex items-center justify-center shadow-md opacity-0 group-hover:opacity-100 translate-y-1 group-hover:translate-y-0 transition-all duration-200 hover:bg-[#2D5E3A] hover:text-white"
          aria-label="Adicionar ao carrinho"
        >
          <ShoppingCart size={16} />
        </button>
      </div>

      {/* Conteúdo */}
      <div className="p-4 flex flex-col gap-2 flex-1">
        <span className="text-[11px] font-semibold text-[#7A9A80] uppercase tracking-wider">
          {product.category}
        </span>

        <h3 className="text-sm font-semibold text-[#1B3A28] leading-snug line-clamp-2 group-hover:text-[#2D5E3A] transition-colors">
          {product.name}
        </h3>

        {/* Avaliação */}
        <div className="flex items-center gap-1.5 mt-auto">
          <div className="flex items-center gap-0.5">
            {Array.from({ length: 5 }).map((_, i) => (
              <Star
                key={i}
                size={11}
                className={i < Math.round(product.rating) ? 'fill-amber-400 text-amber-400' : 'fill-[#D6DDD0] text-[#D6DDD0]'}
              />
            ))}
          </div>
          <span className="text-[11px] text-[#7A9A80]">
            {product.rating.toFixed(1)} ({product.reviewCount})
          </span>
        </div>

        {/* Preço + botão */}
        <div className="flex items-center justify-between mt-1 pt-3 border-t border-[#EBE9E3]">
          <span className="text-base font-bold text-[#1B3A28]">
            {formatPrice(product.price)}
          </span>
          <button className="flex items-center gap-1.5 bg-[#2D5E3A] hover:bg-[#1B3A28] text-white text-xs font-semibold px-3 py-2 rounded-lg transition-colors duration-200">
            <ShoppingCart size={13} />
            Adicionar
          </button>
        </div>
      </div>
    </div>
  )
}
