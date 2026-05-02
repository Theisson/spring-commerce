import { Truck, ShieldCheck, RotateCcw, Package } from 'lucide-react'

const stats = [
  { icon: Truck, text: 'Frete grátis acima de R$ 200' },
  { icon: ShieldCheck, text: 'Compra 100% segura' },
  { icon: RotateCcw, text: 'Devolução em até 30 dias' },
  { icon: Package, text: 'Milhares de produtos disponíveis' },
]

export function StatsBar() {
  return (
    <div className="bg-[#1B3A28] w-full">
      <div className="max-w-screen-xl mx-auto px-6 lg:px-16 h-[72px] flex items-center">
        <div className="w-full grid grid-cols-2 md:grid-cols-4 gap-4">
          {stats.map((stat, index) => (
            <div key={index} className="flex items-center gap-2.5">
              <stat.icon size={18} className="text-[#7A9A80] shrink-0" />
              <span className="text-white/80 text-[13px] leading-snug">{stat.text}</span>
            </div>
          ))}
        </div>
      </div>
    </div>
  )
}
